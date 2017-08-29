[![Build Status](https://travis-ci.org/Balachandarsmr/PokemonShowdownAndroidClient.svg?branch=master)](https://travis-ci.org/Balachandarsmr/PokemonShowdownAndroidClient)
Protocol
========

Pokémon Showdown's protocol is relatively simple.

Pokémon Showdown is implemented in SockJS. SockJS is a compatibility
layer over raw WebSocket, so you can actually connect to Pokémon
Showdown directly using WebSocket:

    ws://sim.smogon.com:8000/showdown/websocket
      or
    wss://sim.smogon.com/showdown/websocket

Client implementations you might want to look at for reference include:

- pickdenis' chat bot (Ruby) -
    https://github.com/pickdenis/ps-chatbot
- Quinella and TalkTakesTime's chat bot (Node.js) -
    https://github.com/TalkTakesTime/Pokemon-Showdown-Bot
- Nixola's chat bot (Lua) -
    https://github.com/Nixola/NixPSbot
- the official client (HTML5 + JavaScript) -
    https://github.com/Zarel/Pokemon-Showdown-Client

The official client logs protocol messages in the JavaScript console,
so opening that (F12 in most browsers) can help tell you what's going
on.

Client-to-server messages
-------------------------

Messages from the user to the server are in the form:

    ROOMID|TEXT

`ROOMID` can optionally be left blank if it's the lobby, or if the room
is irrelevant (for instance, if `TEXT` is a command like
`/join lobby` where it doesn't matter what room it's sent from, you can
just send `|/join lobby`.)

`TEXT` can contain newlines, in which case it'll be treated the same
way as if each line were sent to the room separately.

A partial list of available commands can be found with `/help`.

To log in, look at the documentation for the `|challstr|` server message.


Server-to-client messages
-------------------------

Messages from the server to the user are in the form:

    >ROOMID
    MESSAGE
    MESSAGE
    MESSAGE
    ...

`>ROOMID` and the newline after it can be omitted if the room is lobby
or global. `MESSAGE` cannot start with `>`, so it's unambiguous whether
or not `>ROOMID` has been omitted.

As for the payload: it's made of any number of blocks of data
separated by newlines; empty lines should be ignored. In particular,
it should be treated the same way whether or not it ends in a
newline, and if the payload is empty, the entire message should be ignored.

If `MESSAGE` doesn't start with `|`, it should be shown directly in the
room's log. Otherwise, it will be in the form:

    |TYPE|DATA

For example:

    |j| Some dude
    |c|@Moderator|hi!
    |c| Some dude|you suck and i hate you!
    Some dude was banned by Moderator.
    |l| Some dude
    |b|battle-ou-12| Cool guy|@Moderator

This might be displayed as:

    Some dude joined.
    @Moderator: hi!
    Some dude: you suck and i hate you!
    Some dude was banned by Moderator.
    Some dude left.
    OU battle started between Cool guy and Moderator

For bandwidth reasons, five of the message types - `chat`, `join`, `leave`,
`name`, and `battle` - are sometimes abbreviated to `c`, `j`, `l`, `n`,
and `b` respectively. All other message types are written out in full so they
should be easy to understand.

Four of these can be uppercase: `J`, `L`, `N`, and `B`, which are
the equivalent of their lowercase versions, but are recommended not to be
displayed inline because they happen too often. For instance, the main server
gets around 5 joins/leaves a second, and showing that inline with chat would
make it near-impossible to chat.

Some outgoing message types
---------------------------

`USER` = a user, the first character being their rank (users with no rank are
represented by a space), and the rest of the string being their username.

####Room initialization

`|init|ROOMTYPE`

> The first message received from a room when you join it. `ROOMTYPE` is
> one of: `chat` or `battle`

`|users|USERLIST`

> `USERLIST` is a comma-separated list of `USER`s, sent from chat rooms when
> they're joined.

####Room messages

`||MESSAGE` or `MESSAGE`

> We received a message `MESSAGE`, which should be displayed directly in
> the room's log.

`|html|HTML`

> We received an HTML message, which should be sanitized and displayed
> directly in the roo
