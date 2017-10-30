var app = angular.module('PeopleApp', []);
app.controller('PeopleCtrl', PeopleCtrl);

function PeopleCtrl($scope,$http,$document,factory1,factory3){
    p1 = new Person(1,"m",true);
    p2 = new Person(2,"f",true);
    $scope.persons = {p1, p2};
    $scope.persons[2] = new Person(3,"f",true);
    console.log("factory3: " + factory3);
}



app.factory('factory2', function factory2Factory(){
    return 'factory22';
});

app.factory('factory3', ['factory1',
    function(factory1) {
        return (factory1 + " factory3");
    }]);
app.factory('factory1', function factory1Factory(){
    return 'factory1';
});
class Person {

    constructor(id, gender, alive){
        this.id = id;
        this.gender = gender;
        this.aliva = alive;
    }

    getGender(){
        return this.gender;
    }
}

let p = new Person(1,"m",true);
console.log(p);
console.log(p.gender);