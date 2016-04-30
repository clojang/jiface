# jiface

[![Clojars Project](https://img.shields.io/clojars/v/clojang/jiface.svg)](https://clojars.org/clojang/jiface)

[![][jiface-logo]][jiface-logo-large]

[jiface-logo]: resources/images/jiface-logo-250x.png
[jiface-logo-large]: resources/images/jiface-logo-1000x.png

*Erlang's JInterface in Idiomatic Clojure*


#### Contents

* [Introduction](#introduction-)
* [Dependencies](#dependencies-)
* [Building](#building-)
* [Documentation](#documentation-)
* [Usage](#usage-)
* [Running Tests](#running-tests-)
* [Erlang, Clojure, and JInterface](#erlang-clojure-and-jinterface-)
* [License](#license-)


## Introduction [&#x219F;](#contents)

This project provides a solution to the [aesthetic problem of JInterface](https://github.com/clojang/clojang/wiki/Example:-JInterface-in-Clojure). While JInterface is an invaluable tool for projects that need to have JVM and Erlang VM languages communicating with each other, it is rather verbose and cumbersom to do so in Clojure. The syntatical burden is often enough to discourage experimentation and play -- essential ingrediates for innovation. The primary goal of jiface is to make it not only easy to write for the Clojure/Erlang interface, but fun as well.

**Low-level API**

The first step towards that was to write a Clojure wrapper for JInterface -- a low-level one that is essentially identical to native JInterface. This will be useful for anyone from a functional programming background who wants low-level access to JInterface via idiomatic Clojure. This is the jiface project.

**Mid-level API**

The second step was to use that low-level API to create a "mid-level" API, one that automatically performned the necessary type conversions of function parameters and returned results, allowing one to write the sort of Clojure one would normally do, without having to cast to Erlang types as is necessary in the low-level Clojure API. This work is captured in a separate project, [Clojang](https://github.com/clojang/clojang).

The mid-level Clojang API is intended for Clojure application developers who which to integrate with languages running on the Erlang VM without having to compromise on the Clojure side.


## Dependencies [&#x219F;](#contents)

* Java
* Erlang
* lein


## Building [&#x219F;](#contents)

``rebar3`` is used for the top-level builds of the project. It runs ``lein`` under the covers in order to build the Clojure code and create the jiface``.jar`` file. As such, to build everything -- LFE, Erlang, and Clojure -- you need only do the following:

* ``rebar3 compile``

If you wish to build your own JInterface ``.jar`` file and not use the one we've uploaded to Clojars, you'll need to follow the instrucations given in the documentation here:

* [Building JInterface for Clojure](http://clojang.github.io/jiface/current/80-building-jinterface.html)


## Documentation [&#x219F;](#contents)

Project documentation, including jiface API reference docs, Javadocs for JInterface, and the Erlang JInterface User's Guide, is availble here:

* [http://clojang.github.io/jiface/current/](http://clojang.github.io/jiface/current/)

Quick links for the other docs:

* [jiface User Guides](http://clojang.github.io/jiface/current/10-low-level.html) - A translation of the *JInterface User's Guide* (Erlang documantaion) from Java into Clojure
* [JInterface User's Guide](http://clojang.github.io/jiface/current/erlang/jinterface_users_guide.html) - The JInterface documentation provided in Erlang distributions
* [Jinterface Javadocs](http://clojang.github.io/jiface/current/erlang/java) - Javadoc-generated API documentation built from the JInterface source code

Mid-level API docs:

* [Clojang User Guides](http://clojang.github.io/clojang/current/10-low-level.html) - An adaptation of the *jiface User's Guide* for the mid-level idiomatic Clojure API


## Usage [&#x219F;](#contents)

Using jiface in a project is just like any other Clojure library. Just add the following to the ``:dependencies`` in your ``project.clj`` file:

[![Clojars Project](https://img.shields.io/clojars/v/clojang/jiface.svg)](https://clojars.org/clojang/jiface)

For the Erlang/LFE side of things, you just need to add the Github URL to your ``rebar.config`` file, as with any other rebar-based Erlang VM project.

As for actual code usage, the documentation section provides links to developer guides and API references, but below are also provided two quick examples, one each in the low- and mid-level APIs.

```clojure
(require '[jiface.otp.messaging :as messaging]
         '[jiface.otp.nodes :as nodes]
         '[jiface.erlang.types :as types]
         '[jiface.erlang.tuple :as tuple-type])
(def node (nodes/node "gurka"))
(def mbox (messaging/mbox node))
(messaging/register-name mbox "echo")
(def msg (into-array
           (types/object)
           [(messaging/self mbox)
            (types/atom "hello, world")]))
(messaging/! mbox "echo" "gurka" (types/tuple msg))
(messaging/receive mbox)
#object[com.ericsson.otp.erlang.OtpErlangTuple
        0x4c9e3fa6
        "{#Pid<gurka@mndltl01.1.0>,'hello, world'}"]
```

From LFE:

```cl
(lfe@mndltl01)> (! #(echo gurka@mndltl01) `#(,(self) hej!))
#(<0.35.0> hej!)
```

Then back in Clojure:

```clojure
(def data (messaging/receive mbox))
(def lfe-pid (tuple-type/get-element data 0))
(messaging/! mbox lfe-pid (types/tuple msg))
```

Then, back in LFE:

```cl
(lfe@mndltl01)> (c:flush)
Shell got {<5926.1.0>,'hello, world'}
```


## Running Tests [&#x219F;](#contents)

All the tests may be run with just one command:

```bash
$ rebar3 eunit
```

This will not only run Erlang and LFE unit tests, it also runs the Clojure unit tests for jiface.

**Clojure Test Selectors**

If you would like to be more selective in the types of jiface tests which get run, you may be interested in reading this section.

The jiface tests use metadata annotations to indicate whether they are unit, system, or integration tests. to run just the unit tests, you can do any one of the following, depending upon what you're used to:

```bash
$ lein test
$ lein test :unit
$ lein test :default
```

To run just the system tests:

```bash
$ lein test :system
```

And, similarly, just the integration tests:

```bash
$ lein test :integtration
```

To run everything:

```bash
$ lein test :all
```

This is what is used by the ``rebar3`` configuration to run the jiface tests.


## Erlang, Clojure, and JInterface [&#x219F;](#contents)

If you are interested in building your own JInterface ``.jar`` file for use with a Clojure project, be sure fo check out the page [Building JInterface for Clojure](https://clojang.github.io/jiface/current/80-building-jinterface.html) on the jiface docs site.


## License [&#x219F;](#contents)

```
Copyright © 2015-2016 Duncan McGreggor

Distributed under the Apache License Version 2.0.
```
