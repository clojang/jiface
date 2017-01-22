(ns jiface.epmd
  (:import [com.ericsson.otp.erlang OtpEpmd]))

(defprotocol EpmdObject
  "Provides methods for registering, unregistering and looking up nodes with
  the Erlang portmapper daemon (Epmd). For each registered node, Epmd
  maintains information about the port on which incoming connections are
  accepted, as well as which versions of the Erlang communication protocol
  the node supports.

  Nodes wishing to contact other nodes must first request information from
  Epmd before a connection can be set up, however this is done automatically
  by ``OtpSelf.connect()`` when necessary.

  The methods ``publishPort()`` and ``unPublishPort()`` will fail if an Epmd
  process is not running on the localhost. Additionally ``lookupPort()`` will
  fail if there is no Epmd process running on the host where the specified node
  is running. See the Erlang documentation for information about starting Epmd.

  This class contains only static methods, there are no constructors.")

(defn lookup-names
  ([] (OtpEpmd/lookupNames))
  ([inet-addr] (OtpEpmd/lookupNames inet-addr))
  ([inet-addr transport] (OtpEpmd/lookupNames inet-addr transport)))

(defn lookup-port
  "Determine what port a node listens for incoming connections on."
  [node]
  (OtpEpmd/lookupPort node))

(defn publish-port
  "Register with Epmd, so that other nodes are able to find and connect to
  it."
  [node]
  (OtpEpmd/publishPort node))

(defn unpublish-port
  "Unregister from Epmd."
  [node]
  (OtpEpmd/unPublishPort node))

(defn use-port
  "Set the port number to be used to contact the epmd process."
  [port-num]
  (OtpEpmd/useEpmdPort port-num))
