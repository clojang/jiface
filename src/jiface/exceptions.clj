(ns jiface.exceptions
  (:require [jiface.erlang :as erlang]
            [jiface.util :as util])
  (:import [clojure.lang Keyword Reflector]
           [com.ericsson.otp.erlang
             OtpErlangExit
             OtpErlangPid]))

;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;;; Exception constructors
;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

(defn exit
  "Constructor for `OtpErlangExit`.

  * `reason` may be of type `OtpErlangObject` or `String`
  * `pid` is optional, but if provided, must be of type `OtpErlangPid`"
  ([reason]
    (erlang/create :exit reason))
  ([reason ^OtpErlangPid pid]
    (erlang/create :exit reason pid)))

(defprotocol ExitObject
  "Exception raised when a communication channel is broken. This can be caused
  for a number of reasons, for example:

  * an error in communication has occurred
  * a remote process has sent an exit signal
  * a linked process has exited"
  (get-pid [this]
    "Get the pid of the process that sent this exit.")
  (get-reason [this]
    "Get the reason associated with this exit signal."))

(def exit-behaviour
  {:get-pid (fn [this] (.pid this))
   :get-reason (fn [this] (.reason this))})

(extend OtpErlangExit ExitObject exit-behaviour)

;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
;;; Error handling
;;; >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

(util/add-err-handler #'erlang/create
  [java.lang.IllegalArgumentException,
   java.lang.InstantiationException]
  "[ERROR] could not instantiate object!")
