app.factory('apiEngine', function apiEngine($http){
	var mock = true;
	
    return {
        people: function(func){
            console.log("Requesting /persons/");
            if(mock){
            	var jsonResponse = {};
            	jsonResponse.data = {
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
            		    	 }
            		    	 ;
            	func(jsonResponse);
            } else{
            	return $http.get("http://home.maartenschipper.com:8002/PeopleFarmMock/status.jsp?task=getAll").then(func);
            	//return $http.get("http://home.maartenschipper.com:8002/PeopleFarmMock/status.jsp?task=getAll").then(func);
            }
        },
        status: function(id, func){
            var url = "http://home.maartenschipper.com:8002/PeopleFarmMock/status.jsp?id=" + id + "";
            console.log("Requesting /person/status/:" + id);
            console.log("-> on " + url)
            return $http.get(url).then(func);
        },
        updateStatus: function(id, func){
            var url = "http://home.maartenschipper.com:8002/PeopleFarmMock/status.jsp?id=" + id + "&task=updatePerson";
            console.log("Requesting /person/status/:" + id);
            console.log("-> on " + url)
            return $http.get(url).then(func);
        },
        task: function(id, func){
            var url = "http://home.maartenschipper.com:8002/PeopleFarmMock/task.jsp?id=" + id;
            console.log("Requesting /person/task/:" + id);
            console.log("-> on " + url)
            return $http.get(url).then(func);
        },
        reset: function(func){
            var url = "http://home.maartenschipper.com:8002/PeopleFarmMock/status.jsp?task=reset";
            console.log("Requesting reset");
            return $http.get(url).then(func);
        },
        setTask: function(id, task, func){
            var url = "http://home.maartenschipper.com:8002/PeopleFarmMock/status.jsp?id=" + id + "&task=" + task;
            console.log("Requesting /person/settask/:" + task + "/" + id);
            console.log("-> on " + url)
            return $http.get(url).then(func);
        }
    };
});