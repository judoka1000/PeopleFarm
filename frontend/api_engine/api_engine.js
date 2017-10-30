app.factory('apiEngine', function apiEngine($http){

    return {
        people: function(func){
            console.log("Requesting /persons/");
            return $http.get("http://home.maartenschipper.com:8002/PeopleFarmMock/status.jsp?task=getAll").then(func);
            //return $http.get("http://home.maartenschipper.com:8002/PeopleFarmMock/status.jsp?task=getAll").then(func);
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