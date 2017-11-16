
app.factory('gameEngine', function gameEngine() {
    var sessionFlag = true;

    return {
        enableAPI: function() {
            sessionFlag = true;
        },
        disableAPI: function() {
            sessionFlag = false;
        },
        getAPI: function() {
            return sessionFlag;
        }
    };
});

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

        personStatus: function(id,func,errFunc=function(){}){
            var functionPath = "/person/" + id;
            //console.log("Requesting " + functionPath);
            return $http.get(baseUrl + functionPath).then(func, errFunc);
        },

        personTask: function(id,func){
            var functionPath = "/person/task/:" + id;
            //console.log("Requesting " + functionPath);
            if(mock) func(getMockData("/person/task"));
            else return $http.get(baseUrl + "status.jsp?task=getAll").then(func);
        },

        personSettask: function(id,task,func){
            var functionPath = "/person/settask/" + task + "/" + id;
            console.log("Requesting " + functionPath);
            return $http.put(baseUrl + functionPath).then(func);
        },

        personSetTwoTask: function(id1, id2, task,func){
            var functionPath = "/person/settask/" + task + "/" + id1 + "/" + id2;
            console.log("Requesting " + functionPath);
            return $http.put(baseUrl + functionPath).then(func);
        },

        delete: function(id,func){
            var functionPath = "/person/" + id;
            //console.log("Requesting " + functionPath + " (delete)");
            return $http.delete(baseUrl + functionPath).then(func);
        },
        getScore: function(func, errFunc=function(){}){
            var url = baseUrl + "/score";
            //console.log("Requesting score");
            return $http.get(url).then(func, errFunc);
        },
        newGame: function(func) {
            var url = baseUrl + "/newgame";
            console.log("Requesting /newgame");
            return $http.post(url).then(func);
        },
        renamePlayer: function(name, func) {
            var url = baseUrl + "/rename/" + name;
            return $http.post(url).then(func);
        },
        getPlayername: function(func) {
            var url = baseUrl + "/rename";
            return $http.get(url).then(func);
        },
        getAccounts: function(func) {
            var url = baseUrl + "/accounts";
            return $http.get(url).then(func);
        },
        getButtons: function(func) {
            var url = baseUrl + "/buttons";
            console.log("Requesting " + url);
            return $http.get(url).then(func);
        },
        getShopButtons: function(func) {
            var url = baseUrl + "/shop/buttons";
            console.log("Requesting " + url);
            return $http.get(url).then(func);
        },
        buy: function(id,func) {
            var url = baseUrl + "/shop/buy/" + id;
            console.log("Requesting " + url);
            return $http.get(url).then(func);
        },
        getGameInfo: function(func) {
            var url = baseUrl + "/gameinfo";
            console.log("Requesting " + url);
            return $http.get(url).then(func);
        }
    };
});