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

type Create struct {
	ClientName string `json:"clientName"`
	ClientID   string `json:"clientID"`
	ClientDoB  string `json:"clientDoB"`
}

var createCommand Create

func init() {
	createCommand = Create{}
	flag.StringVar(&createCommand.ClientName, "name", "", "Client Name")
	flag.StringVar(&createCommand.ClientID, "id", "", "Client NRIC/FIN")
	flag.StringVar(&createCommand.ClientDoB, "dob", "", "Client Date of Birth")
	flag.Parse()
}

func main() {
	var url string

	url = os.Getenv("MOAB_URL")

	if url == "" {
		url = "http://localhost:8080/api/v1/account/"
	}
	payloadString, err := json.Marshal(createCommand)
	if err != nil {
		log.Fatalf("Failed to parse your magic command! %v\n", err.Error())
	}

	payload := strings.NewReader(string(payloadString))

	req, _ := http.NewRequest("POST", url, payload)

	req.Header.Add("content-type", "application/json")
	req.Header.Add("cache-control", "no-cache")

	res, err := http.DefaultClient.Do(req)

	if err != nil {
		fmt.Println("No response")
		os.Exit(1)
	}

	defer res.Body.Close()
	body, _ := ioutil.ReadAll(res.Body)

	fmt.Println(res)
	fmt.Println(string(body))
}