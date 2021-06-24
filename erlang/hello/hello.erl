-module(hello).
-export([hello_world/0]).
-export([start/0]).

hello_world() -> io:fwrite("hello, world\n").

start() ->
   Str1 = "This is a string", 
   io:fwrite("~p~n",[Str1]).