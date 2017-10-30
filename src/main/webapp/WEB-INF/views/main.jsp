<!DOCTYPE html>
<html>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.4/angular.min.js"></script>

<head>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0-beta.2/css/bootstrap.min.css" integrity="sha384-PsH8R72JQ3SOdhVi3uxftmaW6Vc51MKb0q5P2rRUpPvrszuE4W1povHYgTpBfshb" crossorigin="anonymous">
    <link rel="stylesheet" href="css/main.css">

    <script type="text/javascript" src="controller/controller.js"></script>
    <script type="text/javascript" src="api_engine/api_engine.js"></script>
    <script type="text/javascript" src="api_engine/mock.js"></script>

    <script type="text/javascript" src="person/person.js"></script>

</head>
<body ng-app="PeopleApp" ng-controller="PeopleCtrl" ng-class="cursor">

<div id="PeopleGrid">
    <div id="people_{{person.id}}" class="people" ng-class="'sprite' + person.sprite + ' ' + person.fullGender" ng-repeat="person in persons" ng-show="person.visible">
        <div class="imgContainer" ng-click="personClicked(person)">

        </div>
        <div class="overview">
            <div class="title"><h1>Worker &num;{{("000" + p.id).slice(-4)}}</h1></div>
            <p class="percLabel">Tiredness:</p><div class="percBarContainer {{getBarPc(person.status.tiredness)}}"><div ng-style="{'width': person.status.tiredness + '%' }">{{person.status.tiredness}}</div></div>
            <p class="percLabel">Hunger:</p><div class="percBarContainer {{getBarPc(person.status.hungry)}}"><div ng-style="{'width': person.status.hungry + '%' }">{{person.status.hungry}}</div></div>

        </div>
    </div>
</div>
<div id="hud">
    <div id="actionEating" class="actionButton" ng-click="setClickAction('eat')"></div>
    <div id="actionSleeping" class="actionButton" ng-click="setClickAction('sleep')"></div>
    <div id="actionNone" class="actionButton" ng-click="actionNone()"></div>
    <div id="actionKill" class="actionButton" ng-click="setClickAction('kill')"></div>
    <div id="actionUpdate" class="actionButton" ng-click="actionUpdate()"></div>
</div>

</body>
</html>