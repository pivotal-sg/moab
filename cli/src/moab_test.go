package main

import (
	"testing"
	. "github.com/onsi/gomega"
)

func TestShowRequest(t *testing.T) {
	RegisterTestingT(t)
	show := Show{AccountNumber: "123"}
	url := "http://localhost:8080/api/v1"

	request := ShowRequest(show, url)
	Expect(request.Method).To(Equal("GET"))
	Expect(request.URL.String()).To(Equal(url + "/123"))
}

func TestCreateRequest(t *testing.T) {
	RegisterTestingT(t)
	create := Create{
		ClientName:"name",
		ClientID:"id",
		ClientDoB:"1960-01-01",
	}
	url := "http://localhost:8080/api/v1"

	request := CreateRequest(create, url)
	Expect(request).ToNot(BeNil())
	Expect(request.Method).To(Equal("POST"))
	Expect(request.URL.String()).To(Equal(url))
}

