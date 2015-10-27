#TXHCF - Staff Mode plugin

###Implements:
* Staff mode for moderators
    * Freezing players
    * Report wrapper for corereport
    * Viewing of player inventories
    * Teleporting to a random player on the server

###Coming soon:
* Staff chat for staff (`/sc` command)
* Commands for staff mode utilities (random teleport, invsee)

###Commands:
* `/freeze` - Freeze a player
    * No parameters: Unfreeze yourself if you have the permission `staff.freeze`
    * One parameter: Player name as arg[0]: Freezes player arg[0]
* `/staff` - Toggle staff mode for a player
    * No parameters: Toggle staff mode for yourself
    * One parameter: Player name as arg[0]: Toggle staff mode for player arg[0]

###Permissions:
* `staff.on` - Force a player into staff mode on login, player cannot leave staff mode.
* `staff.toggle` - Allow a player to toggle staff mode for themself.
* `staff.others` - Allow a player to toggle staff mode for other players (other player does not need any permission to be put into staff mode).
* `staff.sc` - Allow a player to send and recieve messages in staff chat.
* `staff.freeze` - Freeze command permission
