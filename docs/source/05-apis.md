# The APIs

There are three APIs to chose from if you wish to interact with
Erlang-compatible nodes from the JVM:

* `JInterface` - The Ericsson/Erlang-maintained Java implementation of the
  Erlang communication protocol
* `jiface` - A Clojure library that provides a very thin idomatic Clojure
  wrapper around `JInterface`
* `clojang` - A Clojure library that attempts to make communications with
  Erlang from Clojure as Clojure-native feeling as possible, eliminating as
  much boilerplate as possible

The differences are discussed below and demonstrated with a working example,
contrasted in all three.


## JInterface

JInterface is an invaluable tool for projects that need to have JVM and Erlang
VM languages communicating with each other. However, it is rather verbose
(regardless of which JVM language you use) and feels cumbersome to use from
Clojure. The syntatical burden and often anti-inuitive usage is sometimes
enough to discourage experimentation and play (the latter being an essential
ingrediates for innovation :-)).

The code below should be contrasted with the two examples for Clojang
which have been written in its low- and high-level APIs. It is meant to show
the awkwardness of using JInterface from Clojure. Clojure applications built
using JInterface get very ugly, very quickly, especially with all the parsing
of Erlang data types (JInterface objects).

```clojure
(import '[com.ericsson.otp.erlang
           OtpErlangObject
           OtpErlangAtom
           OtpErlangTuple
           OtpNode
           OtpMbox])

(def gurka (new OtpNode "gurka"))
(def inbox (.createMbox gurka))
(.registerName inbox "echo")

(def msg (into-array
           OtpErlangObject
           [(.self inbox)
            (new OtpErlangAtom "hello, world")]))

(.send inbox "echo" "gurka" (new OtpErlangTuple msg))
(.receive inbox)

#object[com.ericsson.otp.erlang.OtpErlangTuple
        0x4c9e3fa6
        "{#Pid<gurka@host.1.0>,'hello, world'}"]
```

From LFE (started with `-sname lfe`):

```cl
(clojang-lfe@host)> (! #(echo gurka@host) `#(,(self) hej!))
#(<0.35.0> hej!)
```

Then back in Clojure:

```clj
(def data (.receive inbox))
(def lfe-pid (.elementAt data 0))
(def lfe-msg (-> data
                 (.elementAt 1)
                 (.toString)
                 (clojure.string/replace "'" "")
                 (keyword)))
(.send inbox lfe-pid (new OtpErlangTuple msg))
```

Finally, back in LFE:

```cl
(clojang-lfe@host)> (c:flush)
Shell got {<5926.1.0>,'hello, world'}
```


## Low-level Clojure API

The first step towards making JInterface more usable from Clojure was to write
a Clojure wrapper for it -- a low-level one that is essentially identical to
native JInterface, but utilises Clojure idioms. This will be useful for anyone
from a functional programming background who wants low-level access to
JInterface via idiomatic Clojure. This is the
[jiface](https://github.com/clojang/jiface) project.

Here is the example above, rewritten using `jiface`:

```clj
(require '[jiface.erlang.tuple :as tuple]
         '[jiface.erlang.types :as types]
         '[jiface.otp.nodes :as nodes]
         '[jiface.otp.messaging :as messaging])

(def gurka (nodes/node "gurka"))
(def inbox (nodes/create-mbox gurka))
(messaging/register-name inbox "echo")

(def msg (into-array
           (types/object)
           [(messaging/self inbox)
            (types/atom "hello, world")]))

(messaging/send inbox "echo" "gurka" (types/tuple msg))
(messaging/receive inbox)
```

From LFE (started with `-sname lfe`):

```cl
(clojang-lfe@host)> (! #(echo gurka@host) `#(,(self) hej!))
#(<0.35.0> hej!)
```

Then back in Clojure:

```clj
(def data (messaging/receive inbox))
(def lfe-pid (tuple/get-element data 0))
(def lfe-msg (-> data
                 (tuple/get-element 1)
                 (str)
                 (clojure.string/replace "'" "")
                 (keyword)))
(messaging/send inbox lfe-pid (types/tuple msg))
```

Finally, back in LFE:

```cl
(clojang-lfe@host)> (c:flush)
Shell got {<5926.1.0>,'hello, world'}
```

As you can see, the `jiface` version of the example is essentially the same
thing -- as it should be -- with the difference being that instead of calling
methods on Java objects, you're calling Clojure functions (well, protocol
implementations). It's a bit clearner and certainly something Clojurians will
be more comfortable with, but we've only dealt with one layer of translation:
from Java to Clojure. There's still another one: how to make a Java translation
of Erlang more sensible in Clojure. Ideally, it would be as simple and
straight-forward as when connecting to nodes from LFE.


## High-level Clojure API

The next step is to use a higher-level API that wraps the low-level API, one
that automatically performns the necessary type conversions of function
parameters and returned results. This would allow one to write the sort of
Clojure one would normally do, without having to cast to Erlang types as is
necessary in the low-level Clojure API. This work is captured in a separate
project, [Clojang](https://github.com/clojang/clojang).

This high-level Clojang API is intended for Clojure application developers who
wish to integrate with languages running on the Erlang VM without having to
compromise on the Clojure side. It is anticipated that `clojang` will be far
preferrable to developers over `jiface`.

Below is the example above, rewritten using `clojang`. Before going over it,
though, you'll note that when you start a `clojang` Clojure REPL, you'll see
the Clojang logo/splash screen. What's happening behind the scenes is that the
Clojang Java agent is starting up a Clojang node in a way analogous to when
you start LFE with the `-sname lfe` parameter. In the latter case, LFE creates
an Erlang VM that's capable of communicating with other nodes. Similarly, what
we have with Clojang is a default Clojure node that is created for us by the
agent (complete with its own mbox) that is capable of communicating with other
nodes.

As such, in this example we don't need to set up a node and a message box for
that node; we'll be using the default one created by the agent). Given this,
we're ready to start sending messages immediately:

```clj
(require '[clojang.core :as clojang :refer [! receive self]])

(! [(self) "hello, world"])
(receive)
```

From LFE (started with `-sname lfe`):

```cl
(clojang-lfe@host)> (register 'lferepl (self))
(clojang-lfe@host)> (! #(default clojang@host) `#(,(self) hej!))
```

Then back in Clojure:

```clj
(def data (receive))
(def lfe-pid (get data 0)
(def lfe-msg (get data 1)
(! :lferepl "clojang-lfe@host" [(self) "hello, world"])
```

Then, back in LFE:

```cl
(clojang-lfe@clojang)> (c:flush)
Shell got {<5926.1.0>,"hello, world"}
```

Finally, with this high-level API, we can see a level of usability for the
Erlang protocol communication in Clojure that is on par with that in LFE
itself.
