function getMockData(path,id=10){
    switch(path) {
        case "/persons":
            var jsonData = {
                "people": [

                    {
                        "id": "0",
                        "gender": "m",
                        "sprite": "3",
                        "status": {
                            "hungry": "55",
                            "tiredness": "55",
                            "age": "61"
                        },
                        "abilities": {
                            "speed": "99",
                            "iq": "105",
                            "metabolisme": "47",
                            "stamina": "87"
                        }
                    },

                    {
                        "id": "1",
                        "gender": "f",
                        "sprite": "6",
                        "status": {
                            "hungry": "55",
                            "tiredness": "55",
                            "age": "75"
                        },
                        "abilities": {
                            "speed": "40",
                            "iq": "84",
                            "metabolisme": "71",
                            "stamina": "64"
                        }
                    },

                    {
                        "id": "2",
                        "gender": "m",
                        "sprite": "7",
                        "status": {
                            "hungry": "55",
                            "tiredness": "55",
                            "age": "44"
                        },
                        "abilities": {
                            "speed": "3",
                            "iq": "103",
                            "metabolisme": "61",
                            "stamina": "81"
                        }
                    },

                    {
                        "id": "3",
                        "gender": "m",
                        "sprite": "5",
                        "status": {
                            "hungry": "55",
                            "tiredness": "55",
                            "age": "20"
                        },
                        "abilities": {
                            "speed": "79",
                            "iq": "90",
                            "metabolisme": "23",
                            "stamina": "42"
                        }
                    },

                    {
                        "id": "4",
                        "gender": "f",
                        "sprite": "8",
                        "status": {
                            "hungry": "55",
                            "tiredness": "55",
                            "age": "52"
                        },
                        "abilities": {
                            "speed": "6",
                            "iq": "126",
                            "metabolisme": "69",
                            "stamina": "93"
                        }
                    },

                    {
                        "id": "5",
                        "gender": "f",
                        "sprite": "6",
                        "status": {
                            "hungry": "55",
                            "tiredness": "55",
                            "age": "54"
                        },
                        "abilities": {
                            "speed": "24",
                            "iq": "94",
                            "metabolisme": "31",
                            "stamina": "62"
                        }
                    },

                    {
                        "id": "6",
                        "gender": "m",
                        "sprite": "5",
                        "status": {
                            "hungry": "55",
                            "tiredness": "55",
                            "age": "20"
                        },
                        "abilities": {
                            "speed": "8",
                            "iq": "92",
                            "metabolisme": "75",
                            "stamina": "93"
                        }
                    },

                    {
                        "id": "7",
                        "gender": "f",
                        "sprite": "9",
                        "status": {
                            "hungry": "55",
                            "tiredness": "55",
                            "age": "61"
                        },
                        "abilities": {
                            "speed": "94",
                            "iq": "113",
                            "metabolisme": "97",
                            "stamina": "38"
                        }
                    },

                    {
                        "id": "8",
                        "gender": "m",
                        "sprite": "0",
                        "status": {
                            "hungry": "55",
                            "tiredness": "55",
                            "age": "91"
                        },
                        "abilities": {
                            "speed": "59",
                            "iq": "98",
                            "metabolisme": "58",
                            "stamina": "19"
                        }
                    },

                    {
                        "id": "9",
                        "gender": "m",
                        "sprite": "5",
                        "status": {
                            "hungry": "75",
                            "tiredness": "75",
                            "age": "96"
                        },
                        "abilities": {
                            "speed": "64",
                            "iq": "88",
                            "metabolisme": "92",
                            "stamina": "1"
                        }
                    }

                ]
            };
        break;

        case "/person/status":
            var jsonData = {
                "id": id,
                "gender": "f",
                "sprite": "6",
                "status": {
                    "hungry": "55",
                    "tiredness": "55",
                    "age": "75"
                },
                "abilities": {
                    "speed": "40",
                    "iq": "84",
                    "metabolisme": "71",
                    "stamina": "64"
                }
            };
        break;
    }

    var jsonReturn = {};
    jsonReturn.data = jsonData;
    return jsonReturn;
}