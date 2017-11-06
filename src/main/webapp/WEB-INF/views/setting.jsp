<script type="text/javascript" src="resources/controller/dev_controller.js"></script>

<div id="settingScreenArea" class="col-md-3">
	<div class="settingScreen">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
  			<li class="nav-item">
    			<a class="nav-link active" id="game-tab" data-toggle="tab" href="#game" role="tab" aria-controls="game" aria-selected="true">Game</a>
  			</li>
  			<li class="nav-item">
    			<a class="nav-link" id="settings-tab" data-toggle="tab" href="#settings" role="tab" aria-controls="settings" aria-selected="false">Settings</a>
  			</li>
  			<li class="nav-item">
                <a class="nav-link" id="dev-settings-tab" data-toggle="tab" href="#dev-settings" role="tab" aria-controls="dev-settings" aria-selected="false">Dev Settings</a>
            </li>
		</ul>
		<div class="tab-content" id="myTabContent">
 			<div class="tab-pane fade show active" id="game" role="tabpanel" aria-labelledby="game-tab">
 				<h1>{{ playername }}&#39;s game</h1>
 				<div class="container">
 				    <div class="row">
         				<div id="gameButtons">
         				    <div id="newGameButton" class="" ng-click="newGameAction()">New Game</div>
                		</div>
 				    </div>
 					<div class="row">
		 				<div id="playerScore">$ {{score}}</div>
		 			</div>
		 			<div class="row">
		 				<div id="actionButtons">
							   <div id="actionEating" class="actionButton" ng-click="setClickAction('eatHamburger')"></div>
							   <div id="actionDogfood" class="actionButton" ng-click="setClickAction('eatDogfood')"></div>
							   <div id="actionSleeping" class="actionButton" ng-click="setClickAction('sleep')"></div>
							   <div id="actionReproducing" class="actionButton" ng-click="setClickAction('reproduce')"></div>
							   <div id="actionNone" class="actionButton" ng-click="actionNone()"></div>
							   <div id="actionKill" class="actionButton" ng-click="setClickAction('kill')"></div>
							   <div id="actionTest" class="actionButton" ng-click="setClickAction('test')">test</div>
							   <div id="actionInfo" class="actionButton" ng-click="setClickAction('info')">info</div>
							   <div id="actionCollect" class="actionButton" ng-click="setClickAction('collect')">collect</div>
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
    		    <input type="text" ng-model="playername" /><button ng-click="renamePlayer(playername)">Rename</button>
    		</div>

 			<div class="tab-pane fade" id="dev-settings" role="tabpanel" aria-labelledby="dev-settings-tab">
 			    <div ng-app="PeopleApp" ng-controller="DevCtrl" ng-class="cursor">
                    <h1>Dev Settings</h1>
                    <div class="form-check">
                      <label class="form-check-label">
                        <input class="form-check-input" type="checkbox" ng-model="devSettings.mortal" ng-change="settingsChanged()">
                        Mortal
                      </label>
                    </div>
                    </br>


                    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#personModal">Create Person</button>

                    <div class="modal fade" id="personModal" tabindex="-1" role="dialog" aria-labelledby="personModalLabel" aria-hidden="true">
                      <div class="modal-dialog" role="document">
                        <div class="modal-content">
                          <div class="modal-header">
                            <h5 class="modal-title" id="personModalLabel">Create a Person!</h5>
                            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                              <span aria-hidden="true">&times;</span>
                            </button>
                          </div>
                          <div class="modal-body">
                                <form name="personForm">
                                    <div class="form-check form-check-inline"  ng-init="newPerson.gender='MALE'">
                                        <label class="form-check-label">
                                            <input class="form-check-input" name="genderRadios" type="radio" id="maleRadio" value="MALE" ng-model="newPerson.gender" ng-checked="true"> Male
                                        </label>
                                    </div>
                                    <div class="form-check form-check-inline">
                                        <label class="form-check-label">
                                            <input class="form-check-input" name="genderRadios" type="radio" id="femaleRadio" value="FEMALE" ng-model="newPerson.gender"> Female
                                        </label>
                                    </div>
                                    <div class="form-group row">
                                        <label for="inputAge" class="col-sm-3 col-form-label">Age</label>
                                        <div class="col-sm-9">
                                            <input name="age" type="number" class="form-control" id="inputAge" placeholder="Age" ng-model="newPerson.status.age" required>
                                            <span ng-show="personForm.age.$touched && personForm.age.$invalid || personForm.age.$error.number">Age is not valid.</span>
                                        </div>
                                    </div>
                                    <div class="form-group row">
                                         <label for="inputIQ" class="col-sm-3 col-form-label">IQ</label>
                                         <div class="col-sm-9">
                                            <input name="iq" type="number" class="form-control" id="inputIQ" placeholder="IQ" ng-model="newPerson.abilities.iq" required>
                                            <span ng-show="personForm.iq.$touched && personForm.iq.$invalid || personForm.iq.$error.number">IQ is not valid.</span>
                                         </div>
                                    </div>
                                    <div class="form-group row">
                                         <label for="inputSpeed" class="col-sm-3 col-form-label">Speed</label>
                                         <div class="col-sm-9">
                                            <input name="speed" type="number" class="form-control" id="inputSpeed" placeholder="Speed" ng-model="newPerson.abilities.speed" required>
                                            <span ng-show="personForm.speed.$touched && personForm.speed.$invalid || personForm.speed.$error.number">Speed is not valid.</span>
                                         </div>
                                    </div>
                                    <div class="form-group row">
                                         <label for="inputMetabolism" class="col-sm-3 col-form-label">Metabolism</label>
                                         <div class="col-sm-9">
                                            <input name="metabolism" type="number" class="form-control" id="inputMetabolism" placeholder="Metabolism" ng-model="newPerson.abilities.metabolism" required>
                                             <span ng-show="personForm.metabolism.$touched && personForm.metabolism.$invalid || personForm.metabolism.$error.number">Metabolism is not valid.</span>
                                         </div>
                                    </div>
                                    <div class="form-group row">
                                         <label for="inputStamina" class="col-sm-3 col-form-label">Stamina</label>
                                         <div class="col-sm-9">
                                            <input name="stamina" type="number" class="form-control" id="inputStamina" placeholder="Stamina" ng-model="newPerson.abilities.stamina" required>
                                            <span ng-show="personForm.stamina.$touched && personForm.stamina.$invalid || personForm.stamina.$error.number">Stamina is not valid.</span>
                                         </div>
                                    </div>
                                    <div class="form-group row">
                                         <label for="inputTiredness" class="col-sm-3 col-form-label">Tiredness</label>
                                         <div class="col-sm-9">
                                            <input name="tiredness" type="number" class="form-control" id="inputTiredness" placeholder="Tiredness" ng-model="newPerson.status.tiredness" required>
                                            <span ng-show="personForm.tiredness.$touched && personForm.tiredness.$invalid || personForm.tiredness.$error.number">Tiredness is not valid.</span>
                                         </div>
                                    </div>
                                    <div class="form-group row">
                                         <label for="inputHunger" class="col-sm-3 col-form-label">Hunger</label>
                                         <div class="col-sm-9">
                                            <input name="hunger" type="number" class="form-control" id="inputHunger" placeholder="Hunger" ng-model="newPerson.status.hunger" required>
                                            <span ng-show="personForm.hunger.$touched && personForm.hunger.$invalid || personForm.hunger.$error.number">Hunger is not valid.</span>
                                         </div>
                                    </div>
                                </form>
                          <div class="modal-footer">
                            <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                            <button type="button" class="btn btn-primary" ng-click="createPerson()" data-dismiss="modal" ng-disabled="personForm.$invalid">Create</button>
                          </div>
                        </div>
                      </div>
                    </div>


                </div>
            </div>
		</div>
	</div>	
</div>