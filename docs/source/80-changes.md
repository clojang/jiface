# Breaking Changes

This page highlights breaking changes introduced in different versions of jiface.


### 0.2.0 -> 0.3.0


#### `init` functions changed to `create`

In both the `jiface.erlang` and `jiface.otp` namespaces, the `init` functions
have been renamed to `create`. An alias is provided, but that may be removed in
the future without warning.


#### Internal use of keywords instead of symbols/strings

If you made direct use of the following low-level functions, your code will
be broken and in need of changing:

* `erlang/init` (now renamed to `erlang/create`)
* `otp/init` (now renamed to `orp/create`)
* Name-creation functions in `util` (now in `core`)

The usage of these functions have changed from:

```clj
(erlang/init 'atom "a")
(otp/init 'node "b")
```

to:

```clj
(erlang/create :atom "a")
(otp/create :node "b")
```

The name-creation functions used to take strings as arguments and now take
keywords as arguments.


#### Utility Function Moves & Renames

All of the name-generating functions that were in the `util` namespace have
been moved into a new `core` namespace. Furthermore, most of these functions
have been renamed and altered significantly (processing keyword arguments
instead of string arguments).


#### Function Removal

Two helper functions (one each in the `erlang` and `otp` namespaces) have
been removed.
