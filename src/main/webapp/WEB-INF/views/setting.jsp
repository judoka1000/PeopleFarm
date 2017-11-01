<div id="settingScreenArea" class="col-md-3">
	<div class="settingScreen">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
  			<li class="nav-item">
    			<a class="nav-link active" id="game-tab" data-toggle="tab" href="#game" role="tab" aria-controls="game" aria-selected="true">Game</a>
  			</li>
  			<li class="nav-item">
    			<a class="nav-link" id="settings-tab" data-toggle="tab" href="#settings" role="tab" aria-controls="settings" aria-selected="false">Settings</a>
  			</li>
		</ul>
		<div class="tab-content" id="myTabContent">
 			<div class="tab-pane fade show active" id="game" role="tabpanel" aria-labelledby="game-tab">
 				<h1>Game</h1>
 				<div class="container">
 				    <div class="row">
         				<div id="gameButtons">
         				    <div id="newGameButton" class="" ng-click="newGameAction()">New Game</div>
                		</div>
                		</br>
 				    </div>
 					<div class="row">
		 				<div id="playerScore">Score : 1337</div>
		 			</div>
		 			<div class="row">
		 				<div id="actionButtons">
							   <div id="actionEating" class="actionButton" ng-click="setClickAction('eat')"></div>
							   <div id="actionSleeping" class="actionButton" ng-click="setClickAction('sleep')"></div>
							   <div id="actionNone" class="actionButton" ng-click="actionNone()"></div>
							   <div id="actionKill" class="actionButton" ng-click="setClickAction('kill')"></div>
							   <div id="actionTest" class="actionButton" ng-click="setClickAction('test')">test</div>
							   <div id="actionInfo" class="actionButton" ng-click="setClickAction('info')">info</div>
						</div>
					</div>
				</div>
				</br>
				</br>
				<div id="peopleStat" ng-if="showPeopleId >= 0">
					<h6>People Statistics:</h6>
					<div>ID : {{persons[showPeopleId].id}}</div>
					<div>Age : {{persons[showPeopleId].status.age}}</div>
					<div>Hunger : {{persons[showPeopleId].status.hunger}}</div>
					<div>Tiredness : {{persons[showPeopleId].status.tiredness}}</div>
					<div>IQ : {{persons[showPeopleId].abilities.iq}}</div>
					<div>Speed : {{persons[showPeopleId].abilities.speed}}</div>
					<div>Metabolism : {{persons[showPeopleId].abilities.metabolism}}</div>
				</div>
 			</div>
 			<div class="tab-pane fade" id="settings" role="tabpanel" aria-labelledby="settings-tab">
 				<h1>Settings</h1>
 			</div>
		</div>
	</div>	
</div>
