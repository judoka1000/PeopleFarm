<script type="text/javascript" src="resources/controller/dev_controller.js"></script>

<div id="settingScreenArea" class="col-md-3">
	<div class="settingScreen">
		<ul class="nav nav-tabs" id="myTab" role="tablist">
			<li class="nav-item">
				<a class="nav-link active" id="game-tab" data-toggle="tab" href="#game" role="tab" aria-controls="game" aria-selected="true">Game</a>
			</li>
  			<li class="nav-item">
    			<a class="nav-link" id="shop-tab" data-toggle="tab" href="#shop" role="tab" aria-controls="shop" aria-selected="true">Shop</a>
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
		 				<div id="actionButtons" ng-if="buttons[0]" >
							<actionbutton ng-repeat="button in buttons" name="button.name" ng-click="setClickAction(button)" src="button.image"></actionbutton>
						</div>
					</div>
				</div>
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
                <div id="comparePeople" ng-if="cp1 >= 0 || cp2 >= 0">
                    <table style="width:100%">
                        <col />
                        <col ng-class="{active : selectedCol == 1}" />
                        <col ng-class="{active : selectedCol == 2}" />
                        <tr>
                            <th></th>
                            <th ng-click="selectCol(1)"><div class="people"><div class="imgContainer" ng-if="cp1 >= 0" ng-class="'sprite' + persons[cp1].sprite + ' ' + 'sprite' + persons[cp1].getAdult()"></div></div></th>
                            <th ng-click="selectCol(2)"><div class="people"><div class="imgContainer" ng-if="cp2 >= 0" ng-class="'sprite' + persons[cp2].sprite + ' ' + 'sprite' + persons[cp2].getAdult()"></div></div></th>
                        </tr>
                        <tr><th>Worker #	</th><th ng-click="selectCol(1)">{{persons[cp1].id}}					    </th><th ng-click="selectCol(2)">{{persons[cp2].id}}					</th></tr>
                        <tr><td>Age : 		</td><td ng-class="getColor('age',1)">{{persons[cp1].status.age}}			</td><td ng-class="getColor('age',2)">{{persons[cp2].status.age}}			</td></tr>
                        <tr><td>Hunger : 	</td><td ng-class="getColor('hunger',1)">{{persons[cp1].status.hunger}}		</td><td ng-class="getColor('hunger',2)">{{persons[cp2].status.hunger}}		</td></tr>
                        <tr><td>Tiredness : </td><td ng-class="getColor('tiredness',1)">{{persons[cp1].status.tiredness}}	    </td><td ng-class="getColor('tiredness',2)">{{persons[cp2].status.tiredness}}	</td></tr>
                        <tr><td>IQ : 		</td><td ng-class="getColor('iq',1)">{{persons[cp1].abilities.iq}}		    </td><td ng-class="getColor('iq',2)">{{persons[cp2].abilities.iq}}		</td></tr>
                        <tr><td>Speed : 	</td><td ng-class="getColor('speed',1)">{{persons[cp1].abilities.speed}}		</td><td ng-class="getColor('speed',2)">{{persons[cp2].abilities.speed}}		</td></tr>
                        <tr><td>Metabolism :</td><td ng-class="getColor('metabolism',1)">{{persons[cp1].abilities.metabolism}} </td><td ng-class="getColor('metabolism',2)">{{persons[cp2].abilities.metabolism}}</td></tr>
                    </table>
                </div>
 			</div>

			<div class="tab-pane fade" id="shop" role="tabpanel" aria-labelledby="shop-tab">
				<div class="shopRow" ng-repeat="button in storeButtons | shopFilter:this" ng-click="button.buyCost<=score && buyItem(button)" ng-class="{disabled: button.buyCost>score}">
					<shopbutton name="button.name" src="button.image"></shopbutton>
					<p>{{button.name}} &#36;{{button.buyCost}}</p>
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
                    <jsp:include page="settings/createperson.jsp" />

                    <div class="form-group">
                        <label for="sessionid">SessionID:</label>
                        <textarea class="form-control" rows="2" id="sessionid" ng-click-copy={{cookieInfo}} readonly>{{cookieInfo}}</textarea>
                    </div>
                </div>
            </div>
		</div>
	</div>	
</div>