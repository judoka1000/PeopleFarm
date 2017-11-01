

app.factory('apiEngine', function apiEngine($http){
    var mock = false;
    var baseUrl = "http://localhost:8080/peoplefarm";

    return {
        people: function(func){
            var functionPath = "/persons";
            //console.log("Requesting " + functionPath + mock ? " (mock)" : "");
            if(mock) func(getMockData(functionPath));
            else return $http.get(baseUrl + functionPath).then(func);
        },

        personStatus: function(id,func){
            var functionPath = "/person/" + id;
            //console.log("Requesting " + functionPath);
            if(mock) func(getMockData("/person/status"));
            else return $http.get(baseUrl + functionPath).then(func);
        },

        personTask: function(id,func){
            var functionPath = "/person/task/:" + id;
            //console.log("Requesting " + functionPath);
            if(mock) func(getMockData("/person/task"));
            else return $http.get(baseUrl + "status.jsp?task=getAll").then(func);
        },

        personSettask: function(id,task,func){
            var functionPath = "/person/settask/" + task + "/" + id;
            //console.log("Requesting " + functionPath);
            return $http.put(baseUrl + functionPath).then(func);
        },

        personSetTwoTask: function(id1, id2, task,func){
            var functionPath = "/person/settask/:" + task + "/:+" + id1 + "/:" + id2;
            console.log("Requesting " + functionPath);
            if(mock) func(getMockData("/person/settask2"));
            else return $http.get(baseUrl + "status.jsp?task=getAll").then(func);
        },

        delete: function(id,func){
            var functionPath = "/person/" + id;
            //console.log("Requesting " + functionPath + " (delete)");
            return $http.delete(baseUrl + functionPath).then(func);
        },

        ppeople: function(func){
            //console.log("Requesting /persons/");
            return $http.get(baseUrl + "status.jsp?task=getAll").then(func);
        },
        updateStatus: function(id, func){
            var url = baseUrl + "status.jsp?id=" + id + "&task=updatePerson";
            //console.log("Requesting /person/status/:" + id);
            //console.log("-> on " + url)
            return $http.get(url).then(func);
        },
        task: function(id, func){
            var url = baseUrl + "task.jsp?id=" + id;
            //console.log("Requesting /person/task/:" + id);
            //console.log("-> on " + url)
            return $http.get(url).then(func);
        },
        reset: function(func){
            var url = baseUrl + "status.jsp?task=reset";
            //console.log("Requesting reset");
            return $http.get(url).then(func);
        },
        setTask: function(id, task, func){
            var url = baseUrl + "status.jsp?id=" + id + "&task=" + task;
            //console.log("Requesting /person/settask/:" + task + "/" + id);
            //console.log("-> on " + url)
            return $http.get(url).then(func);
        },
        getScore: function(func){
        	var url = baseUrl + "/score";
        	//console.log("Requesting score");
        	return $http.get(url).then(func);
        },
        newGame: function(func) {
            var url = baseUrl + "/newgame";
            //console.log("Requesting /newgame");
            return $http.post(url).then(func);
        }
    };
});