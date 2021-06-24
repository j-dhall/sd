%%%-------------------------------------------------------------------
%% @doc hello-new public API
%% @end
%%%-------------------------------------------------------------------

-module(hello-new_app).

-behaviour(application).

-export([start/2, stop/1]).

start(_StartType, _StartArgs) ->
    hello-new_sup:start_link().

stop(_State) ->
    ok.

%% internal functions
