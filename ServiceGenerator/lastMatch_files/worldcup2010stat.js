var cookieDomain='.fifa.com';var baseUrl='http://www.fifa.com';var siteLang='en';var idSplitLen=3;var numWordsComment=100;var commentsPerPage=10;var TeamPage =
{
   teamData: null,
   showdetail: function(statfield, teamid) {
      if (TeamPage.teamData == null) {
         $j.getJSON('/worldcup/statistics/teams/team=' + teamid + '/mstat.txt', function(data) {
            TeamPage.teamData = data;
            TeamPage.showdetail(statfield, teamid);
         });
      } else {
         $j('#statdetail h4').html(TeamPage.teamData[statfield].label);
         if (TeamPage.teamData[statfield].value == '100.00%')
            $j('#statdetail p.big').html('100%');
         else
            $j('#statdetail p.big').html(TeamPage.teamData[statfield].value);
         $j('#statdetail #tDetail tbody').empty();
         $j.each(TeamPage.teamData.matches, function(i, match) {
            $j('<tr><td class="even">' + match.date + '</td>' +
                 '<td class="odd"><img height="13" width="19" alt="' + match.hteamName + '" src="/imgml/flags/s/' + match.hteamcode.toLowerCase() + '.gif" class="flagSmall"/>' + match.hteamcode + '</td>' +
                 '<td class="even">' + match.score + '</td>' +
                 '<td class="odd"><img height="13" width="19" alt="' + match.ateamName + '" src="/imgml/flags/s/' + match.ateamcode.toLowerCase() + '.gif" class="flagSmall"/>' + match.ateamcode + '</td>' +
                 '<td class="value even">' + match[statfield] + '</td>' +
                 '</tr>').appendTo('#statdetail #tDetail tbody');
         });
         $j('#passingDetail').css('display', 'none');
         $j('#statdetail').show('slow');         
      }
      return false;
   }
}
var PlayerPage =
{
   playerData: null,
   showdetail: function(statfield, playerid) {
      if (PlayerPage.playerData == null) {
         $j.getJSON('/worldcup/statistics/players/player=' + playerid + '/mstat.txt', function(data, textStatus) {
            PlayerPage.playerData = data;
            PlayerPage.showdetail(statfield, playerid);
         });
      } else {
         $j('#statdetail h4').html(PlayerPage.playerData[statfield].label);
         if (PlayerPage.playerData[statfield].value == '100.00%')
            $j('#statdetail p.big').html('100%');
         else
            $j('#statdetail p.big').html(PlayerPage.playerData[statfield].value);
         $j('#statdetail').show('slow');
         $j('#statdetail #tDetail tbody').empty();
         $j.each(PlayerPage.playerData.matches, function(i, match) {
            var value = match[statfield];
            if (value == null || value == undefined)
               value = '&nbsp;'
            $j('<tr><td class="even">' + match.date + '</td>' +
                 '<td class="odd"><img height="13" width="19" alt="' + match.hteamName + '" src="/imgml/flags/s/' + match.hteamcode.toLowerCase() + '.gif" class="flagSmall"/>' + match.hteamcode + '</td>' +
                 '<td class="even">' + match.score + '</td>' +
                 '<td class="odd"><img height="13" width="19" alt="' + match.ateamName + '" src="/imgml/flags/s/' + match.ateamcode.toLowerCase() + '.gif" class="flagSmall"/>' + match.ateamcode + '</td>' +
                 '<td class="value even">' + value + '</td>' +
                 '</tr>').appendTo('#statdetail #tDetail tbody');
         });
      }
      return false;
   }
}
var PassingDistributionPage =
{
   passingData: null,
   strPassesTo: "",
   strPassesFrom: "",
   playerId: "",
   showdetail: function(fromTo, playerid) {
      if (PassingDistributionPage.passingData == null) {
         $j.getJSON('/worldcup/statistics/players/player=' + PassingDistributionPage.playerId + '/passingdistributionpermatch.txt', function(data, textStatus) {
            PassingDistributionPage.passingData = data;
            PassingDistributionPage.showdetail(fromTo, playerid);
         });
      } else {
         var data;
         var strLabel;
         if (fromTo == 'from') {
            data = PassingDistributionPage.passingData.passesFrom["pl_" + playerid];
            strLabel = PassingDistributionPage.strPassesFrom;
         } else {
            data = PassingDistributionPage.passingData.passesTo["pl_" + playerid];
            strLabel = PassingDistributionPage.strPassesTo;
         }
         $j('#statdetail h4').html(strLabel + '<br />' + data.name);
         $j('#statdetail p.big').html(data.Pass);
         $j('#statdetail #tSummary tbody').html('<tr><td>' + data.ShortPass + '</td><td>' + data.MediumPass + '</td><td>' + data.LongPass + '</td></tr>');
         $j('#statdetail #tDetail tbody').empty();
         $j.each(data.matches, function(i, match) {
            $j('<tr><td class="even">' + match.MatchDate + '</td>' +
            '<td class="odd"><img height="13" width="19" alt="' + match.HomeTeamName + '" src="/imgml/flags/s/' + match.HomeCountryCode.toLowerCase() + '.gif" class="flagSmall"/>' + match.HomeCountryCode + '</td>' +
            '<td class="even">' + match.ResultStr + '</td>' +
            '<td class="odd"><img height="13" width="19" alt="' + match.AwayTeamName + '" src="/imgml/flags/s/' + match.AwayCountryCode.toLowerCase() + '.gif" class="flagSmall"/>' + match.AwayCountryCode + '</td>' +
            '<td class="value tdTotal">' + match.Pass + '</td>' +
            '<td class="value tdShort">' + match.ShortPass + '</td>' +
            '<td class="value tdMedium">' + match.MediumPass + '</td>' +
            '<td class="value tdLong">' + match.LongPass + '</td>' +
            '</tr>').appendTo('#statdetail #tDetail tbody');
         })
         $j('#statdetail').show('slow');
      }
      return false;
   }
 }
 
 var PlayerCIMatchPage =
{
  playerData: new Object,
  showdetail: function(statfield, playerid, globalCI) {
    if (PlayerCIMatchPage.playerData[playerid] == null) {
      $j.getJSON('/worldcup/statistics/players/player=' + playerid + '/castrolindexpermatch.txt', function(data, textStatus) {
        PlayerCIMatchPage.playerData[playerid] = data;
        PlayerCIMatchPage.showdetail(statfield, playerid, globalCI);
      });
    } else {
    $j('#statdetail h4').html(PlayerCIMatchPage.playerData[playerid].name);
    $j('#statdetail p.big').html(globalCI);
      $j('#statdetail').show('slow');
      $j('#statdetail #tDetail tbody').empty();
      $j.each(PlayerCIMatchPage.playerData[playerid].matches, function(i, match) {
        var value = match[statfield];
        if (value == null || value == undefined)
          value = '&nbsp;'
        $j('<tr><td class="even">' + match.date + '</td>' +
                 '<td class="odd"><img height="13" width="19" alt="' + match.hteamName + '" src="/imgml/flags/s/' + match.hteamcode.toLowerCase() + '.gif" class="flagSmall"/>' + match.hteamcode + '</td>' +
                 '<td class="even">' + match.score + '</td>' +
                 '<td class="odd"><img height="13" width="19" alt="' + match.ateamName + '" src="/imgml/flags/s/' + match.ateamcode.toLowerCase() + '.gif" class="flagSmall"/>' + match.ateamcode + '</td>' +
                 '<td class="value even">' + value + '</td>' +
                 '</tr>').appendTo('#statdetail #tDetail tbody');
      });
    }
    return false;
  }
}
var PlayerTopPassing =
{
   passingData: new Object,
   showdetail: function(playerId) {
      if (PlayerTopPassing.passingData[playerId] == null) {
         $j.getJSON('/worldcup/statistics/players/player=' + playerId + '/topPassingDistribution.txt', function(data, textStatus) {
            PlayerTopPassing.passingData[playerId] = data;
            PlayerTopPassing.showdetail(playerId);
         });
      } else {
         var data = PlayerTopPassing.passingData[playerId];
         $j('#passingDetail .bigAvatar img').attr('src', '/imgml/tournament/worldcup2010/players/s/' + playerId + '.png');
         $j('#passingDetail h4#plName').html(data.name);
         $j('#passingDetail td#totalPass').html(data.totalPass);
         $j('#passingDetail td#totalPassCompleted').html(data.totalPassCompleted);
         $j('#passingDetail td#totalPassCR').html(data.totalPassCR);
         PlayerTopPassing.bindData('#defenders div.container', data.fp_2);
         PlayerTopPassing.bindData('#midfielders div.container', data.fp_3);
         PlayerTopPassing.bindData('#forwards div.container', data.fp_4);
         $j('#passingDetail a#moreLink').attr('href', '/worldcup/statistics/players/player=' + playerId + '/passingdistribution.html');
         $j('#statdetail').css('display', 'none');
         $j('#passingDetail').show('slow');
      }
   },
   bindData: function(elId, data) {
      $j(elId).html(' ');
      if (data)
         for (var i = 0; i < data.length; i++) {
         $j(elId).append('<div class="player">' +
               '<div class="avatar"><img class="portrait" alt="" src="/imgml/tournament/worldcup2010/players/xs/' + data[i].id + '.png"/></div><div class="data">' +
               '<p class="plName">' + data[i].name + '</p>' +
               '<p class="plValue">' + data[i].totalPass + '</p></div>' +
               '</div>');
      }
   }
}
var PlayerComparisonTeaser =
{
   playerComparisonData: new Object,
   baseMoreLinkUrl: '',
   init: function() {
      $j('.bindBar_A, .bindBar_B').each(function() {
         $j(this).data("maxW", $j(this).width()-5);
         $j(this).width("1px");
      });
      PlayerComparisonTeaser.baseMoreLinkUrl = $j('a#playerComparisonTeaserMoreLink').attr('href');
      var teams = $j('select#selectTeam_A > option').length;
      var randA = Math.floor(Math.random() * teams);
      var randB = Math.floor(Math.random() * teams);
      var valA = $j('select#selectTeam_A option:eq(' + randA + ')').val();
      var valB = $j('select#selectTeam_B option:eq(' + randB + ')').val();
      $j('select#selectTeam_A option:eq(' + randA + ')').attr("selected", "selected");
      $j('select#selectTeam_B option:eq(' + randB + ')').attr("selected", "selected");
      PlayerComparisonTeaser.changeTeam('A', valA);
      PlayerComparisonTeaser.changeTeam('B', valB);
      $j('select#selectTeam_A').change(function() { PlayerComparisonTeaser.changeTeam('A', this.options[this.selectedIndex].value) });
      $j('select#selectTeam_B').change(function() { PlayerComparisonTeaser.changeTeam('B', this.options[this.selectedIndex].value) });
      $j('select#selectPlayer_A').change(function() { PlayerComparisonTeaser.changePlayer('A', this.options[this.selectedIndex].value) });
      $j('select#selectPlayer_B').change(function() { PlayerComparisonTeaser.changePlayer('B', this.options[this.selectedIndex].value) });
   },
   changeTeam: function(side, newId) {
      if (PlayerComparisonTeaser.playerComparisonData[newId] != null) {
         $j('select#selectPlayer_' + side).html(PlayerComparisonTeaser.playerComparisonData[newId].options);
         PlayerComparisonTeaser.selectRandomPlayer(side);
      } else {
         $j('select#selectTeam_' + side).attr('disabled', 'disabled');
         $j('select#selectPlayer_' + side).attr('disabled', 'disabled');
         var jsonUrl = "/worldcup/statistics/teams/team=" + newId + "/teamplayersstats.txt";
         $j.getJSON(jsonUrl, function(data, textStatus) {
            var options = '';
            PlayerComparisonTeaser.playerComparisonData[newId] = new Object;
            $j.each(data.items, function(i, item) {
               options += '<option value="' + item.IdPlayer + '" title="' + item.PlayerName + '">' + item.PlayerName + '</option>';
               PlayerComparisonTeaser.playerComparisonData[newId][item.IdPlayer] = item;
            });
            PlayerComparisonTeaser.playerComparisonData[newId].options = options;
            $j('select#selectPlayer_' + side).html(options);
            if (data.items[0] == undefined)
               PlayerComparisonTeaser.changePlayer(side, -1);
            else
               PlayerComparisonTeaser.selectRandomPlayer(side);
            $j('select#selectTeam_' + side).removeAttr('disabled');
            $j('select#selectPlayer_' + side).removeAttr('disabled');
         });
      }
   },
   selectRandomPlayer: function(side) {
      var players = $j('select#selectPlayer_' + side + ' > option').length;
      var randP = Math.floor(Math.random() * players);
      var valP = $j('select#selectPlayer_' + side + ' option:eq(' + randP + ')').val();
      PlayerComparisonTeaser.changePlayer(side, valP);
      if ($j('html.ie6').length > 0) {
         setTimeout(function() { // ugly but needed to fix a bug on ie6
            $j('select#selectPlayer_' + side + ' :selected').removeAttr('selected');
            $j('select#selectPlayer_' + side + ' option:eq(' + randP + ')').attr("selected", "selected");
         }, 5);
      } else {
         $j('select#selectPlayer_' + side + ' :selected').removeAttr('selected');
         $j('select#selectPlayer_' + side + ' option:eq(' + randP + ')').attr("selected", "selected");
      }
   },
   changePlayer: function(side, newId) {
      var teamId = $j('select#selectTeam_' + side + ' :selected').val();
      var data = PlayerComparisonTeaser.playerComparisonData[teamId][newId];
      var otherSide = (side == 'A') ? 'B' : 'A';
      if (data != null) {
         $j('.player_' + side + ' img.plf').attr("src", "/imgml/flags/s/" + data.CountryCode.toLowerCase() + ".gif");
         $j('.player_' + side + ' img.plf').attr("alt", data.CountryCode);
         $j('.player_' + side + ' img.plf').attr("title", data.CountryCode);
         $j('.player_' + side + ' img.plth').attr("src", "/imgml/tournament/worldcup2010/players/xs/" + data.IdPlayer + ".png");
         $j('.player_' + side + ' img.plth').attr("alt", data.PlayerName);
         $j('.player_' + side + ' img.plth').attr("title", data.PlayerName);
         
         for (var item in data) {
            $j('.bind_' + side + '.' + item).html(data[item]);
            if ($j('.bindBar_' + side + '.' + item).length > 0) {
               var bar = $j('.bindBar_' + side + '.' + item);
               var otherBar = $j('.bindBar_' + otherSide + '.' + item);
               var value = Number(data[item]);
               var otherValue = Number($j('.bind_' + otherSide + '.' + item).html());
               if (value == null || isNaN(value) || value == 0) {
                  if (otherValue == null || isNaN(otherValue) || otherValue == 0) {
                     bar.width("0");
                     otherBar.width("0");
                  } else {
                     bar.width("0");
                     otherBar.width(otherBar.data("maxW"));
                  }
               } else {
                  if (otherValue == null || isNaN(otherValue) || otherValue == 0) {
                     bar.width(bar.data("maxW"));
                     otherBar.width("0");
                  } else {
                     bar.width(Math.round(Number(bar.data("maxW")) * value / (value + otherValue)));
                     otherBar.width(Math.round(Number(otherBar.data("maxW")) * otherValue / (value + otherValue)));
                  }
               }
            }
         }
      } else {
         $j('.bind_' + side).each(function() { $j(this).html('0') });
         $j('.bindBar_' + side).each(function() { $j(this).width('0') }); ;
      }
      PlayerComparisonTeaser.changeLink();
   },
   changeLink: function() {
      var destUrl = PlayerComparisonTeaser.baseMoreLinkUrl + '#';
      if ($j('select#selectTeam_A :selected').val() != undefined)
         destUrl += 'tA=' + $j('select#selectTeam_A :selected').val();
      else
         destUrl = PlayerComparisonTeaser.baseMoreLinkUrl;
      if ($j('select#selectTeam_B :selected').val() != undefined)
         destUrl += '&tB=' + $j('select#selectTeam_B :selected').val();
      else
         destUrl = PlayerComparisonTeaser.baseMoreLinkUrl;
      if ($j('select#selectPlayer_A :selected').val() != undefined)
         destUrl += '&pA=' + $j('select#selectPlayer_A :selected').val();
      else
         destUrl = PlayerComparisonTeaser.baseMoreLinkUrl;
      if ($j('select#selectPlayer_B :selected').val() != undefined)
         destUrl += '&pB=' + $j('select#selectPlayer_B :selected').val();
      else
         destUrl = PlayerComparisonTeaser.baseMoreLinkUrl;
      $j('a#playerComparisonTeaserMoreLink').attr('href', destUrl);
   }
}
var PlayerComparison =
{
   teamPlayers: new Object,
   playerData: new Object,
   loadingInProgress: 0,
   nTabs: 3,
   noPlayerSelectedMessage: '',
   samePlayerMessage: '',
   defaultTeamA: undefined,
   defaultTeamB: undefined,
   defaultplayerA: undefined,
   defaultplayerB: undefined,
   compareIfZero: -2,
   init: function(numberOfTabs, noPlayerSelected_Message, samePlayer_Message) {
      $j('.bindBar_A, .bindBar_B').each(function() {
         $j(this).data("maxW", $j(this).width()-5);
         $j(this).width("1px");
      });
      $j('select#selectTeam_A option:eq(0)').attr("selected", "selected");
      $j('select#selectTeam_B option:eq(0)').attr("selected", "selected");
      $j('select#selectTeam_A').change(function() { PlayerComparison.changeTeam('A', this.options[this.selectedIndex].value) });
      $j('select#selectTeam_B').change(function() { PlayerComparison.changeTeam('B', this.options[this.selectedIndex].value) });
      $j('#compareButton').click(function() { PlayerComparison.compare(); });
      PlayerComparison.nTabs = numberOfTabs;
      PlayerComparison.showTab(1);
      PlayerComparison.noPlayerSelectedMessage = noPlayerSelected_Message;
      PlayerComparison.samePlayerMessage = samePlayer_Message;
      $j('div#playerComparisonData').css('display', 'none');
      $j('div#playerComparisonData').css('height', '');
      $j('div#playerComparisonData').css('overflow', 'visible');
      var sharp = window.location.toString().lastIndexOf('#');
      if (sharp != -1) {
         var querystring = window.location.toString().substring(sharp + 1);
         if (querystring.length > 0) {
            var param = querystring.toQueryParams();
            PlayerComparison.defaultTeamA = param.tA;
            PlayerComparison.defaultTeamB = param.tB;
            PlayerComparison.defaultplayerA = param.pA;
            PlayerComparison.defaultplayerB = param.pB;
         }
      }
      var teams = $j('select#selectTeam_A > option').length;
      if (PlayerComparison.defaultTeamA != undefined)
         PlayerComparison.changeTeam('A', PlayerComparison.defaultTeamA);
      else
         PlayerComparison.changeTeam('A', $j('select#selectTeam_A option:eq(' + Math.floor(Math.random() * teams) + ')').val());
      if (PlayerComparison.defaultTeamB != undefined)
         PlayerComparison.changeTeam('B', PlayerComparison.defaultTeamB);
      else {
         var randB = Math.floor(Math.random() * teams);
         if ($j('select#selectTeam_B option:eq(' + randB + ')').val() == $j('select#selectTeam_A option:selected').val())
            randB = (randB + 1) % teams;
         PlayerComparison.changeTeam('B', $j('select#selectTeam_B option:eq(' + randB + ')').val());
      }
   },
   changeTeam: function(side, newId) {
      if (newId == -1) {
         $j('select#selectPlayer_' + side).html(' ');
      } else {
         if (PlayerComparison.teamPlayers[newId] != null) {
            $j('select#selectPlayer_' + side).html(PlayerComparison.teamPlayers[newId]);
         } else {
            $j('select#selectTeam_' + side + ' option[value=' + newId + ']').attr('selected', 'selected');
            $j('select#selectTeam_' + side).attr('disabled', 'disabled');
            $j('select#selectPlayer_' + side).attr('disabled', 'disabled');
            var jsonUrl = "/worldcup/statistics/teams/team=" + newId + "/teamplayersstats.txt";
            $j.getJSON(jsonUrl, function(data, textStatus) {
               var options = '';
               $j.each(data.items, function(i, item) {
                  options += '<option value="' + item.IdPlayer + '" title="' + item.PlayerName + '">' + item.PlayerName + '</option>';
               });
               PlayerComparison.teamPlayers[newId] = options;
               $j('select#selectPlayer_' + side).html(options);
               $j('select#selectTeam_' + side).removeAttr('disabled');
               $j('select#selectPlayer_' + side).removeAttr('disabled');
               if ($j('html.ie6').length > 0) {
                  setTimeout(function() { // ugly but needed to fix a bug on ie6
                     if (PlayerComparison['defaultplayer' + side] != undefined)
                        $j('select#selectPlayer_' + side + ' option[value=' + PlayerComparison['defaultplayer' + side] + ']').attr('selected', 'selected');
                     else {
                        var players = $j('select#selectPlayer_' + side + ' > option').length;
                        $j('select#selectPlayer_' + side + ' option:eq(' + Math.floor(Math.random() * players) + ')').attr('selected', 'selected');
                     }
                     PlayerComparison.compareIfZero++;
                     if (PlayerComparison.compareIfZero == 0)
                        PlayerComparison.compare();
                  }, 5);
               } else {
                  if (PlayerComparison['defaultplayer' + side] != undefined)
                     $j('select#selectPlayer_' + side + ' option[value=' + PlayerComparison['defaultplayer' + side] + ']').attr('selected', 'selected');
                  else {
                     var players = $j('select#selectPlayer_' + side + ' > option').length;
                     $j('select#selectPlayer_' + side + ' option:eq(' + Math.floor(Math.random() * players) + ')').attr('selected', 'selected');
                  }
                  PlayerComparison.compareIfZero++;
                  if (PlayerComparison.compareIfZero == 0)
                     PlayerComparison.compare();
               }
            });
         }
      }
   },
   compare: function() {
      if ($j('select#selectPlayer_A > option').length == 0 || $j('select#selectPlayer_B > option').length == 0) {
         alert(PlayerComparison.noPlayerSelectedMessage);
      } else {
         var playerA = $j('select#selectPlayer_A :selected').val();
         var playerB = $j('select#selectPlayer_B :selected').val();
         if (playerA == playerB) {
            alert(PlayerComparison.samePlayerMessage);
         } else {
            if (PlayerComparison.playerData[playerA] != null && PlayerComparison.playerData[playerB] != null) {
               PlayerComparison.bindData();
            } else {
               PlayerComparison.showLoadingUI(true);
               if (PlayerComparison.playerData[playerA] == null) {
                  PlayerComparison.loadPlayerData(playerA);
               }
               if (PlayerComparison.playerData[playerB] == null) {
                  PlayerComparison.loadPlayerData(playerB);
               }
            }
         }
      }
   },
   loadPlayerData: function(player) {
      PlayerComparison.loadingInProgress++;
      var jsonUrl = "/worldcup/statistics/players/player=" + player + "/playerstats.txt";
      $j.getJSON(jsonUrl, function(data, textStatus) {
         PlayerComparison.playerData[player] = data;
         PlayerComparison.loadingInProgress--;
         if (PlayerComparison.loadingInProgress == 0) {
            PlayerComparison.showLoadingUI(false);
            PlayerComparison.bindData();
         }
      });
   },
   showLoadingUI: function(flag) {
      if (flag == true) {
         $j('select#selectTeam_A').attr('disabled', 'disabled');
         $j('select#selectTeam_B').attr('disabled', 'disabled');
         $j('select#selectPlayer_A').attr('disabled', 'disabled');
         $j('select#selectPlayer_B').attr('disabled', 'disabled');
         $j('.compareButton').css('display', 'none');
         $j('#playerComparisonData').css('display', 'none');
      } else {
         $j('select#selectTeam_A').removeAttr('disabled');
         $j('select#selectTeam_B').removeAttr('disabled');
         $j('select#selectPlayer_A').removeAttr('disabled');
         $j('select#selectPlayer_B').removeAttr('disabled');
         $j('.compareButton').css('display', 'block');
         $j('#playerComparisonData').css('display', 'block');
      }
   },
   showTab: function(divNumber) {
      for (var i = 1; i <= PlayerComparison.nTabs; i++) {
         if (i == divNumber) {
            $j('#tab' + i).addClass('current');
            $j('#tab' + i + '_content').css('display', 'block');
         } else {
            $j('#tab' + i).removeClass('current');
            $j('#tab' + i + '_content').css('display', 'none');
         }
      }
   },
   bindData: function() {
      var playerA = $j('select#selectPlayer_A :selected').val();
      var playerB = $j('select#selectPlayer_B :selected').val();
      var valA, valB;
      var barA, barB;
      for (var item in PlayerComparison.playerData[playerA]) {
         valA = PlayerComparison.playerData[playerA][item];
         valB = PlayerComparison.playerData[playerB][item];
         $j('.bind_A.' + item).html(valA);
         $j('.bind_B.' + item).html(valB);
         if ($j('.bindBar_A.' + item).length > 0) {
            valA = Number(valA);
            valB = Number(valB);
            barA = $j('.bindBar_A.' + item);
            barB = $j('.bindBar_B.' + item);
            if (valA == null || isNaN(valA) || valA == 0) {
               if (valB == null || isNaN(valB) || valB == 0) {
                  barA.width("0");
                  barB.width("0");
               } else {
                  barA.width("0");
                  barB.width(barB.data("maxW"));
               }
            } else {
               if (valB == null || isNaN(valB) || valB == 0) {
                  barA.width(barA.data("maxW"));
                  barB.width("0");
               } else {
                  barA.width(Math.round(Number(barA.data("maxW")) * valA / (valA + valB)));
                  barB.width(Math.round(Number(barB.data("maxW")) * valB / (valA + valB)));
               }
            }
         }
      }
      $j('.player_A img.plf').attr("src", "/imgml/flags/s/" + PlayerComparison.playerData[playerA].CountryCode.toLowerCase() + ".gif");
      $j('.player_A img.plf').attr("alt", PlayerComparison.playerData[playerA].CountryCode);
      $j('.player_A img.plf').attr("title", PlayerComparison.playerData[playerA].CountryCode);
      $j('.player_A img.plth').attr("src", "/imgml/tournament/worldcup2010/players/m/" + PlayerComparison.playerData[playerA].IdPlayer + ".png");
      $j('.player_A img.plth').attr("alt", PlayerComparison.playerData[playerA].PlayerName);
      $j('.player_A img.plth').attr("title", PlayerComparison.playerData[playerA].PlayerName);
      $j('.player_A a.linkToPlayer').attr("href", "/worldcup/statistics/players/player=" + PlayerComparison.playerData[playerA].IdPlayer + "/index.html");
      $j('.player_A a.linkToTeam').attr("href", "/worldcup/statistics/teams/team=" + PlayerComparison.playerData[playerA].IdTeam + "/index.html");
      $j('.player_B img.plf').attr("src", "/imgml/flags/s/" + PlayerComparison.playerData[playerB].CountryCode.toLowerCase() + ".gif");
      $j('.player_B img.plf').attr("alt", PlayerComparison.playerData[playerB].CountryCode);
      $j('.player_B img.plf').attr("title", PlayerComparison.playerData[playerB].CountryCode);
      $j('.player_B img.plth').attr("src", "/imgml/tournament/worldcup2010/players/m/" + PlayerComparison.playerData[playerB].IdPlayer + ".png");
      $j('.player_B img.plth').attr("alt", PlayerComparison.playerData[playerB].PlayerName);
      $j('.player_B img.plth').attr("title", PlayerComparison.playerData[playerB].PlayerName);
      $j('.player_B a.linkToPlayer').attr("href", "/worldcup/statistics/players/player=" + PlayerComparison.playerData[playerB].IdPlayer + "/index.html");
      $j('.player_B a.linkToTeam').attr("href", "/worldcup/statistics/teams/team=" + PlayerComparison.playerData[playerB].IdTeam + "/index.html");
   }
}
var PlayerComparisonFixedPlayer =
{
   playerComparisonData: new Object,
   baseMoreLinkUrl: '',
   init: function(teamId, playerId) {
      $j('.bindBar_A, .bindBar_B').each(function() {
         $j(this).data("maxW", $j(this).width()-5);
         $j(this).width("1px");
      });
      
      PlayerComparisonFixedPlayer.baseMoreLinkUrl = $j('a#playersComparisonMoreLink').attr('href');      
      $j('#selectTeamAndPlayer').change(function() { PlayerComparisonFixedPlayer.changePlayer('B', this.options[this.selectedIndex].value) });
      // TODO: PRIMA caricare A, POI caricare un player random SOLO DOPO che A è caricato
      // perché se A non ha statistiche può andare tutto a banane
      PlayerComparisonFixedPlayer.changePlayer('A', teamId + ';' + playerId);
      var teams = $j('#selectTeamAndPlayer optgroup').length;
      var randT = Math.floor(Math.random() * teams);
      var players = $j('#selectTeamAndPlayer optgroup:eq(' + randT + ') option').length;
      var randP = Math.floor(Math.random() * players);
      var valP = $j('#selectTeamAndPlayer optgroup:eq(' + randT + ') option:eq(' + randP + ')').val();
      PlayerComparisonFixedPlayer.changePlayer('B', valP);
      $j('#selectTeamAndPlayer optgroup:eq(' + randT + ') option:eq(' + randP + ')').attr("selected", "selected");
      
   },
   changePlayer: function(side, selectedValue) {
      var valueSplit = selectedValue.split(';');
      var teamId = valueSplit[0];
      var playerId = valueSplit[1];
      if (PlayerComparisonFixedPlayer.playerComparisonData[teamId] == null) {
         var jsonUrl = "/worldcup/statistics/teams/team=" + teamId + "/teamplayersstats.txt";
         $j('#selectTeamAndPlayer').attr('disabled', 'disabled');
         $j.getJSON(jsonUrl, function(data, textStatus) {
            PlayerComparisonFixedPlayer.playerComparisonData[teamId] = new Object;
            $j.each(data.items, function(i, item) {
               PlayerComparisonFixedPlayer.playerComparisonData[teamId][item.IdPlayer] = item;
            });
            PlayerComparisonFixedPlayer.changePlayer(side, selectedValue);
            $j('#selectTeamAndPlayer').removeAttr('disabled');
         });
      } else {
         var data = PlayerComparisonFixedPlayer.playerComparisonData[teamId][playerId];
         var otherSide = (side == 'A') ? 'B' : 'A';
         if (data != null) {
            $j('.pl' + side + ' img.flagSmall').attr("src", "/imgml/flags/s/" + data.CountryCode.toLowerCase() + ".gif");
            $j('.pl' + side + ' img.flagSmall').attr("alt", data.CountryCode);
            $j('.pl' + side + ' img.flagSmall').attr("title", data.CountryCode);
            $j('.pl' + side + ' img.plImg').attr("src", "/imgml/tournament/worldcup2010/players/xs/" + data.IdPlayer + ".png");
            $j('.pl' + side + ' img.plImg').attr("alt", data.PlayerName);
            $j('.pl' + side + ' img.plImg').attr("title", data.PlayerName);            
            
            for (var item in data) {
               $j('.bind_' + side + '.' + item).html(data[item]);
               if ($j('.bindBar_' + side + '.' + item).length > 0) {
                  var bar = $j('.bindBar_' + side + '.' + item);
                  var otherBar = $j('.bindBar_' + otherSide + '.' + item);
                  var value = Number(data[item]);
                  var otherValue = Number($j('.bind_' + otherSide + '.' + item).html());
                  if (value == null || isNaN(value) || value == 0) {
                     if (otherValue == null || isNaN(otherValue) || otherValue == 0) {
                        bar.width("0");
                        otherBar.width("0");
                     } else {
                        bar.width("0");
                        otherBar.width(otherBar.data("maxW"));
                     }
                  } else {
                     if (otherValue == null || isNaN(otherValue) || otherValue == 0) {
                        bar.width(bar.data("maxW"));
                        otherBar.width("0");
                     } else {
                        bar.width(Math.round(Number(bar.data("maxW")) * value / (value + otherValue)));
                        otherBar.width(Math.round(Number(otherBar.data("maxW")) * otherValue / (value + otherValue)));
                     }
                  }
               }
            }
         }
         
         var destUrl = PlayerComparisonFixedPlayer.baseMoreLinkUrl + '&tB=' + teamId + '&pB=' + playerId;
         $j('a#playersComparisonMoreLink').attr('href', destUrl);         
         
      }
   }
}
var TeamComparison =
{
   teamData: new Object,
   loadingInProgress: 0,
   nTabs: 3,
   noTeamSelectedMessage: '',
   sameTeamMessage: '',
   defaultTeamA: undefined,
   defaultTeamB: undefined,
   arabic: false,
   imgBasePath: '/imgml/flags/worldfootball',
   imgExt: '.gif',
   
   init: function(numberOfTabs, noTeamSelected_Message, sameTeam_Message, currentLanguage) {
      $j('.bindBar_A, .bindBar_B').each(function() {
         $j(this).data("maxW", $j(this).width()-5);
         $j(this).width("1px");
      });
      $j('#compareButton').click(function() { TeamComparison.compare(); });
      TeamComparison.nTabs = numberOfTabs;
      TeamComparison.showTab(1);
      TeamComparison.noTeamSelectedMessage = noTeamSelected_Message;
      TeamComparison.sameTeamMessage = sameTeam_Message;
      TeamComparison.arabic = (currentLanguage.toLowerCase() == 'a');
      $j('div#teamComparisonData').css('display', 'none');
      $j('div#teamComparisonData').css('height', '');
      $j('div#teamComparisonData').css('overflow', 'visible');
      var sharp = window.location.toString().lastIndexOf('#');
      if (sharp != -1) {
         var querystring = window.location.toString().substring(sharp + 1);
         if (querystring.length > 0) {
            var param = querystring.toQueryParams();
            TeamComparison.defaultTeamA = param.tA;
            TeamComparison.defaultTeamB = param.tB;
         }
      }
      var teams = $j('select#selectTeam_A > option').length;
      if (TeamComparison.defaultTeamA != undefined)
         $j('select#selectTeam_A option[value=' + TeamComparison.defaultTeamA + ']').attr('selected', 'selected');
      else
         $j('select#selectTeam_A option:eq(' + Math.floor(Math.random() * teams) + ')').attr('selected', 'selected');
      if (TeamComparison.defaultTeamB != undefined)
         $j('select#selectTeam_B option[value=' + TeamComparison.defaultTeamB + ']').attr('selected', 'selected');
      else {
         var teamB = Math.floor(Math.random() * teams);
         if ($j('select#selectTeam_B option:eq(' + teamB + ')').val() == $j('select#selectTeam_A :selected').val())
            teamB = (teamB + 1) % teams;
         $j('select#selectTeam_B option:eq(' + teamB + ')').attr('selected', 'selected');
      }
      TeamComparison.compare();
   },
   compare: function() {
      var teamA = $j('select#selectTeam_A :selected').val();
      var teamB = $j('select#selectTeam_B :selected').val();
      if (teamA == "" || teamA <= 0 || teamB == "" || teamB <= 0) {
         alert(TeamComparison.noTeamSelectedMessage);
      } else if (teamA == teamB) {
         alert(TeamComparison.sameTeamMessage);
      } else {
         if (TeamComparison.teamData[teamA] != null && TeamComparison.teamData[teamB] != null) {
            TeamComparison.bindData();
         } else {
            if (TeamComparison.teamData[teamA] == null) {
               TeamComparison.loadTeamData(teamA);
            }
            if (TeamComparison.teamData[teamB] == null) {
               TeamComparison.loadTeamData(teamB);
            }
         }
      }
   },
   showLoadingUI: function(flag) {
      if (flag == true) {
         $j('select#selectTeam_A').attr('disabled', 'disabled');
         $j('select#selectTeam_B').attr('disabled', 'disabled');
         $j('.compareButton').css('display', 'none');
         $j('#teamComparisonData').css('display', 'none');
      } else {
         $j('select#selectTeam_A').removeAttr('disabled');
         $j('select#selectTeam_B').removeAttr('disabled');
         $j('.compareButton').css('display', 'block');
         $j('#teamComparisonData').css('display', 'block');
      }
   },
   showTab: function(divNumber) {
      for (var i = 1; i <= TeamComparison.nTabs; i++) {
         if (i == divNumber) {
            $j('#tab' + i).addClass('current');
            $j('#tab' + i + '_content').css('display', 'block');
         } else {
            $j('#tab' + i).removeClass('current');
            $j('#tab' + i + '_content').css('display', 'none');
         }
      }
   },
   loadTeamData: function(team) {
      TeamComparison.loadingInProgress++;
      var jsonUrl = "/worldcup/statistics/teams/team=" + team + "/teamstats.txt";
      $j.getJSON(jsonUrl, function(data, textStatus) {
         TeamComparison.teamData[team] = data;
         TeamComparison.loadingInProgress--;
         if (TeamComparison.loadingInProgress == 0) {
            TeamComparison.showLoadingUI(false);
            TeamComparison.bindData();
         }
      });
   },
   bindData: function() {
      var teamA = $j('select#selectTeam_A :selected').val();
      var teamB = $j('select#selectTeam_B :selected').val();
      var valA, valB;
      var barA, barB;
      for (var item in TeamComparison.teamData[teamA]) {
         valA = TeamComparison.teamData[teamA][item];
         valB = TeamComparison.teamData[teamB][item];
         $j('.bind_A.' + item).html(valA);
         $j('.bind_B.' + item).html(valB);
         if ($j('.bindBar_A.' + item).length > 0) {
            valA = Number(valA);
            valB = Number(valB);
            barA = $j('.bindBar_A.' + item);
            barB = $j('.bindBar_B.' + item);
            if (valA == null || isNaN(valA) || valA == 0) {
               if (valB == null || isNaN(valB) || valB == 0) {
                  barA.width("0");
                  barB.width("0");
               } else {
                  barA.width("0");
                  barB.width(barB.data("maxW"));
               }
            } else {
               if (valB == null || isNaN(valB) || valB == 0) {
                  barA.width(barA.data("maxW"));
                  barB.width("0");
               } else {
                  barA.width(Math.round(Number(barA.data("maxW")) * valA / (valA + valB)));
                  barB.width(Math.round(Number(barB.data("maxW")) * valB / (valA + valB)));
               }
            }
         }
      }
      /*changed /home with /away and viceversa for arabic*/
      if (TeamComparison.arabic) {
         $j('.team_A img.plf').attr("src", TeamComparison.imgBasePath + "/away/" + TeamComparison.teamData[teamA].CountryCode.toString().toLowerCase() + TeamComparison.imgExt);
         $j('.team_B img.plf').attr("src", TeamComparison.imgBasePath + "/home/" + TeamComparison.teamData[teamB].CountryCode.toString().toLowerCase() + TeamComparison.imgExt);
      } else {
      $j('.team_A img.plf').attr("src", TeamComparison.imgBasePath + "/home/" + TeamComparison.teamData[teamA].CountryCode.toString().toLowerCase() + TeamComparison.imgExt);
      $j('.team_B img.plf').attr("src", TeamComparison.imgBasePath + "/away/" + TeamComparison.teamData[teamB].CountryCode.toString().toLowerCase() + TeamComparison.imgExt);
      }
      $j('.team_A img.plf').attr("alt", TeamComparison.teamData[teamA].CountryCode.toString());
      $j('.team_A img.plf').attr("title", TeamComparison.teamData[teamA].CountryCode.toString());
      $j('.team_B img.plf').attr("alt", TeamComparison.teamData[teamB].CountryCode.toString());
      $j('.team_B img.plf').attr("title", TeamComparison.teamData[teamB].CountryCode.toString());
   }
}
var TeamComparisonTeaser =
{
   baseMoreLinkUrl: '',
   
   init: function() {
      $j('.bindBar_A, .bindBar_B').each(function() {
         $j(this).data("maxW", $j(this).width()-5);
         $j(this).width("1px");
      });
      
      TeamComparisonTeaser.baseMoreLinkUrl = $j('a#teamComparisonTeaserMoreLink').attr('href');      
      $j('#selectTeam_A, #selectTeam_B').change(TeamComparisonTeaser.bindData);
      
      var teams = $j('#selectTeam_A option').length;
      var randA = Math.floor(Math.random() * teams);
      var randB = Math.floor(Math.random() * teams);
      if(randB == randA) randB = (randB + 1) % teams;
      $j('select#selectTeam_A option:eq(' + randA + ')').attr('selected', 'selected');
      $j('select#selectTeam_B option:eq(' + randB + ')').attr('selected', 'selected');
      TeamComparisonTeaser.bindData();
   },
   bindData: function() {
      var teamA = $j('select#selectTeam_A :selected').val();
      var teamB = $j('select#selectTeam_B :selected').val();
      var valA, valB;
      var barA, barB;
      for (var item in TeamComparisonTeaserData[teamA]) {
         valA = TeamComparisonTeaserData[teamA][item];
         valB = TeamComparisonTeaserData[teamB][item];
         
         $j('.bind_A.' + item).html(valA);
         $j('.bind_B.' + item).html(valB);
         if ($j('.bindBar_A.' + item).length > 0) {
            valA = Number(valA);
            valB = Number(valB);
            barA = $j('.bindBar_A.' + item);
            barB = $j('.bindBar_B.' + item);
            if (valA == null || isNaN(valA) || valA == 0) {
               if (valB == null || isNaN(valB) || valB == 0) {
                  barA.width("0");
                  barB.width("0");
               } else {
                  barA.width("0");
                  barB.width(barB.data("maxW"));
               }
            } else {
               if (valB == null || isNaN(valB) || valB == 0) {
                  barA.width(barA.data("maxW"));
                  barB.width("0");
               } else {
                  barA.width(Math.round(Number(barA.data("maxW")) * valA / (valA + valB)));
                  barB.width(Math.round(Number(barB.data("maxW")) * valB / (valA + valB)));
               }
            }
         }
      }
      $j('.player_A img.plf').attr("src", "/imgml/flags/s/" + TeamComparisonTeaserData[teamA].CountryCode.toLowerCase() + ".gif");
      $j('.player_A img.plf').attr("alt", TeamComparisonTeaserData[teamA].CountryCode);
      $j('.player_A img.plf').attr("title", TeamComparisonTeaserData[teamA].CountryCode);
      $j('.player_B img.plf').attr("src", "/imgml/flags/s/" + TeamComparisonTeaserData[teamB].CountryCode.toLowerCase() + ".gif");
      $j('.player_B img.plf').attr("alt", TeamComparisonTeaserData[teamB].CountryCode);
      $j('.player_B img.plf').attr("title", TeamComparisonTeaserData[teamB].CountryCode);
      $j('a#teamComparisonTeaserMoreLink').attr('href', TeamComparisonTeaser.baseMoreLinkUrl + '#tA=' + teamA + '&tB=' + teamB);
   }
}
var PlayerTop11 =
{
   showRandomPlayer: function() {
      var players = $j('div.top11Stats').length;
      PlayerTop11.showPlayer($j('div.top11Stats:eq(' + Math.floor(Math.random() * players) + ')').attr('id'));
   },
   showPlayer: function(playerId) {
      $j('div.top11Stats').css('display', 'none');
      $j('div.top11Stats#' + playerId).css('display', 'block');
      if($j('div.top11Stats#' + playerId + ' img.plth').attr('src') == 'void.png')
         $j('div.top11Stats#' + playerId + ' img.plth').attr('src', '/imgml/tournament/worldcup2010/players/m/' + playerId + '.png');
   }
 }
var GoalRadarPage =
{
  teamData: null,
//  $(function(){
//  $("select#ctlJob").change(function(){
//    $.getJSON("/select.php",{id: $(this).val(), ajax: 'true'}, function(j){
//      var options = '';
//      for (var i = 0; i < j.length; i++) {
//        options += '<option value="' + j[i].optionValue + '">' + j[i].optionDisplay + '</option>';
//      }
//      $("select#ctlPerson").html(options);
//    })
//  })
//})
  refreshMatches: function(teamid, selectMatchTxt) {
      $j.getJSON('/worldcup/statistics/goalradar/library/_,team=' + teamid + ',matches.txt', function(data) {
      var options = '<option>'+selectMatchTxt+'</option>';
      for (var i = 0; i < data.length; i++) {
        options += '<option value="' + data[i].optionValue + '">' + data[i].optionDisplay + '</option>';
      }
      $j("#goalRadarTeamsList").html(options);
      });
    return false;
  },
  refreshMatchesOld: function(teamid) {
    if (GoalRadarPage.teamData == null) {
      $j.getJSON('/worldcup/statistics/goalradar/library/_,team=' + teamid + ',matches.txt', function(data) {
        GoalRadarPage.teamData = data;
        GoalRadarPage.refreshMatches(teamid);
      });
    } else {
      var options = '';
      for (var i = 0; i < GoalRadarPage.teamData.length; i++) {
        options += '<option value="' + GoalRadarPage.teamData[i].optionValue + '">' + GoalRadarPage.teamData[i].optionDisplay + '</option>';
      }
      $j("#goalRadarTeamsList").html(options);
    }
    return false;
  }
}
var ulListPlayersCurrent;
var ulListPlayersItemsCount;
var ulListPlayersContainerWidth;
var leftRight;
function ulListPlayerInit(leftRight) {
   leftRight = leftRight || "left";
   ulListPlayersCurrent = 0;
   ulListPlayersItemsCount = $j('#ulListPlayers > li').length
   ulListPlayersContainerWidth = $j('#ulListPlayers').width();
   $j('#ulListPlayers').width($j('#ulListPlayers').width() * (ulListPlayersItemsCount));
   $j('#ulListPlayers').css(leftRight, "0");
   $j('div.statsData li').corner("6px");
}
function ulListPlayersScrollLeft(leftRight) {
  leftRight = leftRight || "left";
   if (ulListPlayersCurrent > 0) {
     ulListPlayersCurrent--;
     if (leftRight == "left") {
       $j('#ulListPlayers').animate({ left: "-" + (ulListPlayersContainerWidth * ulListPlayersCurrent) + "px" }, { duration: 500 });
     } else {
       $j('#ulListPlayers').animate({ right: "-" + (ulListPlayersContainerWidth * ulListPlayersCurrent) + "px" }, { duration: 500 });
   }
   }
}
function ulListPlayersScrollRight(leftRight) {
  leftRight = leftRight || "left";
   if (ulListPlayersCurrent < ulListPlayersItemsCount - 1) {
     ulListPlayersCurrent++;
     if (leftRight == "left") {
      $j('#ulListPlayers').animate({ left: "-" + (ulListPlayersContainerWidth * ulListPlayersCurrent) + "px" }, { duration: 500 });
     } else {
      $j('#ulListPlayers').animate({ right: "-" + (ulListPlayersContainerWidth * ulListPlayersCurrent) + "px" }, { duration: 500 });
     }
   }
}

