package main

import (
	"encoding/json"
	"flag"
	"fmt"
	"io/ioutil"
	"log"
	"net/http"
	"os"
	"strings"
)

type Account struct {
	ClientName string `json:"clientName"`
	ClientID   string `json:"clientID"`
	ClientDoB  string `json:"clientDoB"`
	AccountNumber string `json:"accountNumber"`
	Balance int `json:"balance"`
}

type Create struct {
	ClientName string `json:"clientName"`
	ClientID   string `json:"clientID"`
	ClientDoB  string `json:"clientDoB"`
}

type Show struct {
	AccountNumber string
}

var url string

func createCreateCommandFlags() (*Create, *flag.FlagSet) {
	var createCommand Create = Create{}
	var createFlags *flag.FlagSet

	createFlags = flag.NewFlagSet("create", flag.ContinueOnError)
	createFlags.StringVar(&createCommand.ClientName, "name", "", "Client Name")
	createFlags.StringVar(&createCommand.ClientID, "id", "", "Client NRIC/FIN")
	createFlags.StringVar(&createCommand.ClientDoB, "dob", "", "Client Date of Birth")

	return &createCommand, createFlags
}

func createShowCommandFlags() (*Show, *flag.FlagSet) {
	var showCommand Show = Show{}
	var showFlags *flag.FlagSet

	showFlags = flag.NewFlagSet("show", flag.ContinueOnError)
	showFlags.StringVar(&showCommand.AccountNumber, "account", "", "Account Number")

	return &showCommand, showFlags
}

// ShowRequest generates the http.Request needed to be sent to 
// see an account's details.
func ShowRequest(show Show, url string) *http.Request {
	req, _ := http.NewRequest("GET", url + "/" +show.AccountNumber, nil)

	req.Header.Add("cache-control", "no-cache")
	return req
}


// CreateRequest generates the http.Request needed to be sent to 
// create a new Account
func CreateRequest(create Create, url string) *http.Request {
	payloadString, err := json.Marshal(create)
	if err != nil {
		log.Fatalf("Failed to parse your magic command! %v\n", err.Error())
	}

	payload := strings.NewReader(string(payloadString))

	req, _ := http.NewRequest("POST", url, payload)

	req.Header.Add("content-type", "application/json")
	req.Header.Add("cache-control", "no-cache")

	return req
}

func init() {
	url = os.Getenv("MOAB_URL")
	if url == "" {
		url = "http://localhost:8080/api/v1/account/"
	}
}

// DispatchCommand handles the subcommands, dispatching the correct
// action.
func DispatchCommand(args []string) {
	if (len(os.Args) < 3) {
		fmt.Println("Need some commands...")
		os.Exit(1)
	}
	create, createFlags := createCreateCommandFlags()
	show, showFlags := createShowCommandFlags()
	switch os.Args[1] {
		case "create":
			createFlags.Parse(os.Args[2:])
			createAccount(create)
		case "show":
			showFlags.Parse(os.Args[2:])
			showAccount(show)
		default:
			fmt.Println("You need to either `create` or `show`:")
			fmt.Println("\nmoab create usage:")
			createFlags.PrintDefaults()
			fmt.Println("\nmoab show usage:")
			showFlags.PrintDefaults()
			os.Exit(1)
		}
}

// printResponse prints out the response from a prior command,
// pretty formating the JSON output.
func printResponse(res *http.Response, err error) {
	if err != nil {
		fmt.Println("No response")
		os.Exit(1)
	}
	defer res.Body.Close()
	body, _ := ioutil.ReadAll(res.Body)
	var outputData *map[string]interface{} = new(map[string]interface{})

	if err != nil || res.StatusCode >= 400 {
		fmt.Printf("StatusCode: %s\nMessage: %s\n", res.Status, string(body))
		os.Exit(0)
	}

	if err := json.Unmarshal(body, outputData); err != nil {
		fmt.Printf("That response was garbage...: %v\n", err.Error())
	}
	output, _ := json.MarshalIndent(outputData, "", "  ")
	fmt.Println(string(output))
}

func createAccount(createCommand *Create) {
	req := CreateRequest(*createCommand, url)
	res, err := http.DefaultClient.Do(req)
	printResponse(res, err)
}

func showAccount(show *Show) {
	req := ShowRequest(*show, url)
	res, err := http.DefaultClient.Do(req)
	printResponse(res, err)
}

func main() {
	DispatchCommand(os.Args)
}
