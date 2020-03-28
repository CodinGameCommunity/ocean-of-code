<!-- LEAGUES level1 level2 level3 level4 -->
<div id="statement_back" class="statement_back" style="display:none"></div>  <div class="statement-body">
  <!-- BEGIN level1 level2 level3 -->
  <div style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 20px;
        margin-right: 15px;
        margin-left: 15px;
        margin-bottom: 10px;
        text-align: left;">
    <div style="text-align: center; margin-bottom: 6px">
      <img src="//cdn.codingame.com/smash-the-code/statement/league_wood_04.png" />
    </div>
    <p style="text-align: center; font-weight: 700; margin-bottom: 6px;">
      <!-- BEGIN level1 -->
      This is a <b>league-based</b> challenge.
      <!-- END -->
      <!-- BEGIN level2 -->
      Welcome to the Wood1 league!
      <!-- END -->
      <!-- BEGIN level3 -->
      Welcome to the Bronze league!
      <!-- END -->
    </p>
    <span class="statement-league-alert-content">
      <!-- BEGIN level1 -->
      Wood leagues should be considered as a tutorial which lets players discover the different rules of the game. <br>
      In Bronze league, all rules will be unlocked and the real challenge will begin.
      <!-- END -->
      <!-- BEGIN level2 -->
      In Wood 1, you can now use SONAR and SILENCE
      <!-- END -->
      <!-- BEGIN level3 -->
      In Bronze, you can now use MINES. There won't be any additional rules.
      <!-- END -->
    </span>
  </div>
  <!-- END -->
  
  <!-- GOAL -->
    <div class="statement-section statement-goal">
        <h2>
            <span class="icon icon-goal">&nbsp;</span>
            <span>The Goal</span>
        </h2>
        <div class="statement-goal-content">
            This is a two-player game based on the board game Captain Sonar. <br>
            You pilot a submarine.  You know that an enemy is present nearby because you are listening to its radio frequency communication.
            You don't know exactly where it is but you can hear all of its orders.<br>
            You and your opponent start with <const>6</const> hit points. When a player's hit points reach <const>0</const>, that player loses.
        </div>
    </div>
    <!-- RULES -->
    <div class="statement-section statement-rules">
        <h2>
            <span class="icon icon-rules">&nbsp;</span>
            <span>Rules</span>
        </h2>
        <div><div class="statement-rules-content">
            <h2>Environment</h2>
            <p>
            <ul>
                <li>The <b>map</b> is a square tiling: <const>15</const> cells wide and <const>15</const> cells high.  The NW corner is <const>(0,0)</const>; the SW corner is <const>(0,14).</li>
                <li>Each map cell is either open <b>water</b> or an <b>island</b>.  Islands are obstacles.  Submarines cannot move to or through islands.  Nor can torpedoes<!-- BEGIN level3 level4 --> or mines<!-- END -->.</li>
                <li><b>Submarines</b> move on the open water cells of the map. Each player controls one submarine; both submarines move independently.  They can share the same water cell without colliding.</li>
                <li>The map is split in <const>9</const> <b>sectors</b>.  Each sector encompasses a <const>5×5</const> block of <const>25</const> cells.  You can find the sectors' numbering in the viewer; the top left sector is number <const>1</const> and the bottom right sector is number <const>9</const>.</li>
            </ul>
            </p>

            <h2>Game start</h2>
            <p>
                At the beginning of the game, you'll receive a full 15×15 map that indicates the position of the islands.
                You will then decide and reply where you want your submarine's <b>starting position</b> to be. <!-- actual format is *not even* (x,y), just let player read the I/O section or stub -->
            </p>
            <br>
            <h2>Turns</h2>
            <p>This is a turn-based game: each player plays a turn one after the other. The whole game lasts <const>299</const> turns per player. The player with id <const>0</const> begins.</p>
            <p>During your turn, thanks to your radio frequency analysis, you will receive an indication of what your opponent has done.
                For example, you can receive that it moved to the north. It's up to you to use this valuable information to detect where the opponent actually is.
            </p>
            <p>
               During your turn, you must perform <b>at least one action</b>.
            </p>
            <br>
            <h2>Actions</h2>
            <p>
                Actions are your turn's output.  They are provided sequentially, on a single line.  You can chain several of them using a pipe symbol <const>|</const>.</p>
            <p>But you can use each type of action only <b>once per turn</b>.  For example you can <const>MOVE</const> once per turn, and no more.
               If you fail to output a valid action, your action will be forced to <const>SURFACE</const>.
            </p>
            <br>
            <p><strong>Move</strong></p>
            <p>A move action has two effects:
              <ol>
                <li>It moves your submarine by <const>1</const> cell in a chosen direction (north, east, south, west).</li>
                <li>It charges a special power of your choice, <i>i.e.</i> increases its energy level by <const>1</const>.</li>
              </ol>
            </p>
            <p>When you move, you must respect the following rules:
            <ul><li>You cannot move through islands.</li>
                <li>You cannot move to a cell you have visited before.</li>
            </ul>
            <p>You can decide what special power to charge.  The special powers are performed by different devices, who require a different amount of energy to be ready. The energy levels are separate per device, not shared.<br>
            <!-- BEGIN level1 -->
            In this league you can only charge the torpedo.
            <!-- END -->
            <!-- BEGIN level2 -->
            In this league you can charge the torpedo, the sonar and the silence mode.
            <!-- END -->
            <!-- BEGIN level3 level4 -->
            You can charge the torpedo, the sonar, the silence mode and mines.
            <!-- END -->
            </p>
            <br><p><strong>Surface</strong></p>
            The surface action will reset your path of visited cells, so that you can freely move to a cell that you have previously visited.
            But surfacing has major impacts: your opponent will know in which sector you are surfacing, and you will lose <const>1</const> hit point.
            <br>
            <br><p><strong>Torpedo</strong></p>
            The torpedo action requires an energy level of <const>3</const>.
            When fully charged, the torpedo can be fired at an arbitrary water position within a water range of <const>4</const> cells.
            This allows the torpedo's path to contain corners and go around islands, but not through them.
            The following image illustrates the range of a torpedo: <br>
            <img src="https://raw.githubusercontent.com/CodinGameCommunity/ocean-of-code/master/torpedoRange.png">
            <br><p>The damage of the explosion is <const>2</const> hit points on the cell itself and <const>1</const> on its 8 neighbor cells (this includes diagonals). You can also damage yourself with a torpedo.</p>
            <br>
            <!-- BEGIN level2 -->
        <div style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
            <p><strong>Sonar</strong></p>
            <p>The sonar action requires an energy level of <const>4</const>.
              When fully charged, it allows you to check whether the opponent's submarine is in a chosen sector.</p>
            <p>The response will be provided to you in your next turn's input.
            Careful! Said response is in respect to the time you issued the order, so your opponent will have issued and performed orders of its own by the time you receive it.</p>

            <br><p><strong>Silence</strong></p>
            The silence action requires an energy level of <const>6</const>.
            When fully charged, it allows you to move <const>0</const> to <const>4</const> cells in a chosen direction.  Same as with the Move action, you may not visit an island or an already visited cell, be it as a final action destination or a waypoint.<br>
            Your opponent will not know in which direction or how far you've moved.  It <i>will</i> know that you used the silence action, though.
            </div>
           <!-- END -->
           <!-- BEGIN level3 level4 -->
            <p><strong>Sonar</strong></p>
            <p>The sonar action requires an energy level of <const>4</const>.
              When fully charged, it allows you to check whether the opponent's submarine is in a chosen sector.</p>
            <p>The response will be provided to you in your next turn's input.
            Careful! Said response is in respect to the time you issued the order, so the opponent will have issued and performed orders of its own by the time you receive it.</p>

            <br><p><strong>Silence</strong></p>
            The silence action requires an energy level of <const>6</const>.
            When fully charged, it allows you to move <const>0</const> to <const>4</const> cells in a chosen direction.  Same as with the Move action, you may not visit an island or an already visited cell, be it as a final action destination or a waypoint.<br>
            Your opponent will not know in which direction or how far you've moved.  It <i>will</i> know that you used the silence action, though.<br>
          <!-- END -->
            <br>
            <!-- BEGIN level3 -->
            <div style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
            <p><strong>Mine</strong></p>
            The mine action requires an energy level of <const>3</const>.
            The mine can be placed on any cell next to you (north, east, south, west).<br>
            You can't place two own mines on the same cell. However it's possible to place your own mine on an opponent mine or the opponent's submarine itself.<br>
            Mines are only detonated by using the trigger action, not by a submarine moving onto them.<br>

            <br><p><strong>Trigger</strong></p>
            The trigger action will cause a mine to explode. You can only trigger your own mines.<br>
            Like for a torpedo, the explosion has a damage of <const>2</const> on the location of the mine and <const>1</const> damage to nearby cells (including diagonally).<br>
            You can't trigger multiple mines in the same turn. You can't place a mine and trigger it during the same turn. Your mines damage your opponent's submarine and yours indiscriminately.<br>
            </div>
            <!-- END -->
            <!-- BEGIN level4 -->
            <p><strong>Mine</strong></p>
            The mine action requires an energy level of <const>3</const>.
            The mine can be placed on any cell next to you (north, east, south, west).<br>
            You can't place two own mines on the same cell. However it's possible to place your own mine on an opponent mine or the opponent's submarine itself.<br>
            Mines are only detonated by using the trigger action, not by a submarine moving onto them.<br>

            <br><p><strong>Trigger</strong></p>
            The trigger action will cause a mine to explode. You can only trigger your own mines.<br>
            Like for a torpedo, the explosion has a damage of <const>2</const> on the location of the mine and <const>1</const> damage to nearby cells (including diagonally).<br>
            You can't trigger multiple mines in the same turn. You can't place a mine and trigger it during the same turn. Your mines damage your opponent's submarine and yours indiscriminately.<br>

            <!-- END -->

            <br><h2>Orders' visibility</h2>
            The following table shows how different actions will be shown to your opponent:
            <table>
                <tr><th>Your action</th><th>Shown to opponent</th><th>Comment</th></tr>
                <tr>
                    <td><action>MOVE N TORPEDO</action></td>
                    <td><action>MOVE N</action></td>
                    <td>The opponent will <em>not</em> see which power is charged.</td>
                </tr>
                <tr>
                    <td><action>SURFACE</action></td>
                    <td><action>SURFACE 3</action></td>
                    <td>The opponent will see which sector (in this case: <const>3</const>) you surfaced in.</td>
                </tr>
                <tr>
                    <td><action>TORPEDO 3 5</action></td>
                    <td><action>TORPEDO 3 5</action></td>
                    <td>The opponent will see the same order you issued.</const></td>
                </tr>
                <!-- BEGIN level2 -->
                <tr style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
                    <td><action>SONAR 4</action></td>
                    <td><action>SONAR 4</action></td>
                    <td>The opponent will see the same order you issued.<!-- sonar result timing isn't relevant to this table --></td>
                </tr>
                <tr style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
                    <td><action>SILENCE N 4</action></td>
                    <td><action>SILENCE</action></td>
                    <td>The opponent will see that you moved silently&mdash;but not where or how far.</td>
                </tr>
                <!-- END -->
                <!-- BEGIN level3 level4 -->
                <tr>
                    <td><action>SONAR 4</action></td>
                    <td><action>SONAR 4</action></td>
                    <td>The opponent will see the same order you issued.<!-- sonar result timing isn't relevant to this table --></td>
                </tr>
                <tr>
                    <td><action>SILENCE N 4</action></td>
                    <td><action>SILENCE</action></td>
                    <td>The opponent will see that you moved silently&mdash;but not where or how far.</td>
                </tr>
                <!-- END -->
                <!-- BEGIN level3 -->
                <tr style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
                    <td><action>MINE E</action></td>
                    <td><action>MINE</action></td>
                    <td>The opponent will see that you placed a mine&mdash;but not in which direction.</td>
                </tr>
                <tr style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;">
                    <td><action>TRIGGER 3 5</action></td>
                    <td><action>TRIGGER 3 5</action></td>
                    <td>The opponent will see the same order you issued.</td>
                </tr>
                <!-- END -->
                <!-- BEGIN level4 -->
                <tr>
                    <td><action>MINE E</action></td>
                    <td><action>MINE</action></td>
                    <td>The opponent will see that you placed a mine&mdash;but not in which direction.</td>
                </tr>
                <tr>
                    <td><action>TRIGGER 3 5</action></td>
                    <td><action>TRIGGER 3 5</action></td>
                    <td>The opponent will see that you triggered the mine at <const>(3,5)</const>.</td>
                 </tr>
                <!-- END -->
            </table>

        </div>
            <!-- Victory conditions -->
            <div class="statement-victory-conditions">
                <div class="icon victory"></div>
                <div class="blk">
                    <div class="title">Victory Conditions</div>
                    <div class="text">Have more hit points left than your enemy at the end of the game.</div>
                </div>
            </div>
            <!-- Lose conditions -->
            <div class="statement-lose-conditions">
                <div class="icon lose"></div>
                <div class="blk">
                    <div class="title">Lose Conditions</div>
                    <ul>
                        <li><div class="text">Output an invalid command or don't respond in time.</div></li>
                        <li><div class="text">Reach <const>0</const> hit points or lower.</div></li>
                    </ul>
                </div>
            </div>

            <div class="statement-section statement-expertrules">
                <h1>
                    <span class="icon icon-expertrules">&nbsp;</span>
                    <span>Expert Rules</span>
                </h1>

                <div class="statement-expert-rules-content">
                    The source code can be found here: <a href="https://github.com/CodinGameCommunity/ocean-of-code" target="_blank">https://github.com/CodinGameCommunity/ocean-of-code</a>.
                </div>
            </div>
        </div>
    </div>
    <!-- PROTOCOL -->
    <div class="statement-section statement-protocol">
        <h2>
            <span class="icon icon-protocol">&nbsp;</span>
            <span>Game Input</span>
        </h2>
        <!-- Protocol block -->
        <div class="blk">
            <div class="title">Inputs of the first turn</div>
            <div class="text"><span class="statement-lineno">Line 1: </span>
                <var>width</var> <var>height</var> <var>myId</var>, space-separated.<br>
                <ul><li><var>width</var> and <var>height</var> indicate the size of the grid in cells (<const>15</const> <const>15</const>)</li>
                    <li><var>myId</var> indicates your player ID (<const>0</const> or <const>1</const>). The player with ID <const>0</const> begins.</li>
                </ul>
                <span class="statement-lineno">Next <var>height</var> lines:</span> a string of <var>width</var> chars representing the content of each cells of this row: <const>x</const> for an island, <const>.</const> for an empty cell.</div>
        </div>

        <!-- Protocol block -->
        <div class="blk">
            <div class="title">Output for the first turn</div>
            <div class="text">2 space-separated integers <var>x</var> <var>y</var> which represent the coordinates of your starting position. The cell at the specified coordinates should be empty which means no island, but the opponent can choose the same cell!</div>
        </div>

        <!-- Protocol block -->
        <div class="blk">
            <div class="title">Inputs for each turn</div>
            <div class="text"><span class="statement-lineno">Line 1: </span>8 space-separated integers:
                <br> <var>x</var> <var>y</var> <var>myLife</var> <var>oppLife</var> <var>torpedoCooldown</var> <var>sonarCooldown</var> <var>silenceCooldown</var> <var>mineCooldown</var><br>
                <ul>
                    <li><var>x</var> and <var>y</var> represent your current position.</li>
                    <li><var>myLife</var> and <var>oppLife</var> give the number of hit points you and your opponent have left</li>
                    <li><var>torpedoCooldown</var> <var>sonarCooldown</var> <var>silenceCooldown</var> <var>mineCooldown</var> represent the number of charges each device still needs before it can be used.
                        <!-- BEGIN level1 level2 -->
                        Devices unavailable in your league will have a cooldown value of <const>-1</const>.
                        <!-- END -->
                    </li>
                </ul>
                <span class="statement-lineno">Line 2: </span> a string <var>sonarResult</var> which gives you the result of your <action>SONAR</action> action: <const>Y</const> for yes, <const>N</const> for no, <const>NA</const>, if you didn't use the sonar on the previous turn. <br>
                <span class="statement-lineno">Line 3: </span> <var>opponentOrders</var>, a summary of your opponent's actions (separated by a pipe symbol <const>|</const>) during its turn.<br>
                Example: <action>MOVE N|TORPEDO 3 5</action><br>
                This example indicates that your opponent moved to the north and then fired a torpedo to (3,5).<br>
                On the starting player's first turn, this will be the string <const>NA</const>, since the opponent has no previous orders at that point.</div>
        </div>

        <!-- Protocol block -->
        <div class="blk">
            <div class="title">Output for each next turns</div>
            <div class="text">One or multiple commands separated by a pipe symbol <const>|</const>. <br>
              Example: <action>MOVE N TORPEDO | TORPEDO 3 5</action><br>
              These commands move your submarine to the north and then fire a torpedo at (3,5).

                Here are the different available actions:
                <ul>
                    <li><action>MOVE direction power</action></li>
                    <li><action>SURFACE</action></li>
                    <li><action>TORPEDO X Y</action></li>
                    <!-- BEGIN level2 -->
                    <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;"><action>SONAR</action></li>
                    <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;"><action>SILENCE direction distance</action></li>
                    <!-- END -->
                    <!-- BEGIN level3 level4 -->
                    <li><action>SONAR</action></li>
                    <li><action>SILENCE direction distance</action></li>
                    <!-- END -->
                    <!-- BEGIN level3 -->
                    <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;"><action>MINE direction</action></li>
                    <li style="color: #7cc576;
        background-color: rgba(124, 197, 118,.1);
        padding: 2px;"><action>TRIGGER X Y</action></li>
                    <!-- END -->
                    <!-- BEGIN level4 -->
                    <li><action>MINE direction</action></li>
                    <li><action>TRIGGER X Y</action></li>
                    <!-- END -->
                    <li><action>MSG message</action></li>
                </ul>
                Here are the different available powers to charge:
                <ul>
                  <li><const>TORPEDO</const></li>
                  <!-- BEGIN level2 level3 level4 -->
                  <li><const>SONAR</const></li>
                  <li><const>SILENCE</const></li>
                  <!-- END -->
                  <!-- BEGIN level4 -->
                  <li><const>MINE</const></li>
                  <!-- END -->
            </div>
        </div>

        <!-- Protocol block -->
        <div class="blk">
            <div class="title">
                Constraints
            </div>
            <div class="text">
                Response time first turn &le; <const>1000</const> ms
                <br> Response time per turn &le; <const>50</const> ms
            </div>
        </div>
    </div>
</div>
