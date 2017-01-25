(ns jiface.otp.transport
  (:require [jiface.otp :as otp])
  (:import [com.ericsson.otp.erlang
            OtpServerSocketTransport
            OtpSocketTransport
            OtpSocketTransportFactory]))

;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;;; OTP constructors
;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

(defn server-socket-transport
  "Constructor for `OtpServerSocketTransport`."
  [^Integer port]
  (otp/create :server-socket-transport port))

(defn socket-transport
  "Constructor for `OtpSocketTransport`.

  If one arg is passed, it must be of type `java.net.Socket`, thus making the
  constructor a wrapper.

  If two args are passed, the first may be either of type
  `java.net.InetAddress` or `String` and the second must be of type `Integer`."
  [args]
  (apply #'otp/create (into [:socket-transport] args)))

;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;;; OTP protocols
;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

(defprotocol ServerTransportObject
  (accept [this]
    "")
  (close [this port]
    "")
  (get-local-port [this]
    ""))

(def server-transport-behaviour
  {:accept
    (fn [this]
      (.accept this))
   :close
    (fn [this]
      (.close this))
   :get-local-port
     (fn [this]
      (.getLocalPort this))})

(extend OtpServerSocketTransport
        ServerTransportObject
        server-transport-behaviour)

(defprotocol SocketTransportObject
  (close [this port]
    "")
  (get-input-stream [this]
    "")
  (get-output-stream [this]
    ""))

(def socket-transport-behaviour
  {:close
    (fn [this]
      (.close this))
   :get-input-stream
    (fn [this]
      (.getInputStream this))
   :get-output-stream
     (fn [this]
      (.getOutputStream this))})

(extend OtpSocketTransport SocketTransportObject socket-transport-behaviour)

(defprotocol TransportFactory
  "Represents the default socket-based transport factory."
  (create-server-transport [this port]
    "Create an instance of `OtpServerTransport`.")
  (create-transport [this addr port]
    "Create an instance of `OtpTransport`."))

(def transport-factory-behaviour
  {:create-server-transport
    (fn [this ^Integer port]
      (.createServerTransport this port))
   :create-transport
    (fn [this addr ^Integer port]
      (.createTransport this addr port))})

(extend OtpSocketTransportFactory TransportFactory transport-factory-behaviour)

;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;;; Error handling
;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

(util/add-err-handler #'otp/create
  [java.lang.IllegalArgumentException,
   java.lang.InstantiationException]
  "[ERROR] could not instantiate object!")
