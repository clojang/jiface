# The APIs


## JInterface

JInterface is an invaluable tool for projects that need to have JVM and Erlang
VM languages communicating with each other. However, it is rather verbose
(regardless of which JVM language you use) and especially  cumbersome to use
in Clojure. The syntatical burden is often enough to discourage
experimentation and play -- essential ingrediates for innovation.

The code below should be contrasted with the two analog examples for Clojang
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
        "{#Pid<gurka@mndltl01.1.0>,'hello, world'}"]
```

From LFE:

```cl
(lfe@mndltl01)> (! #(echo gurka@mndltl01) `#(,(self) hej!))
#(<0.35.0> hej!)
```

Then back in Clojure:

```clojure
(def data (.receive inbox))
(def lfe-pid (.elementAt data 0))
(def lfe-msg (-> data
                 (.elementAt 1)
                 (.toString)
                 (clojure.string/replace "'" "")
                 (keyword)))
(.send inbox lfe-pid (new OtpErlangTuple msg))
```

Then, back in LFE:

```cl
(lfe@mndltl01)> (c:flush)
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
TBD
```


## High-level Clojure API

The next step was to use that low-level API to create a "high-level" API, one
that automatically performned the necessary type conversions of function
parameters and returned results, allowing one to write the sort of Clojure one
would normally do, without having to cast to Erlang types as is necessary in
the low-level Clojure API. This work is captured in a separate project,
[Clojang](https://github.com/clojang/clojang).

The high-level Clojang API is intended for Clojure application developers who
wish to integrate with languages running on the Erlang VM without having to
compromise on the Clojure side. It is anticipated that clojang will be far
preferred over jiface.

Here is the example above, rewritten using `clojang`:

```clj
TBD
```
