(ns jiface.otp.streams
  (:require
    [jiface.otp :as otp]))

(defn input-stream
  "Constructor for `OtpInputStream`.

  If one arg is passed, it must be an array of bytes.

  If two args are passed, the first must be an array of bytes and the
  second must be the flags (an `Integer`).

  If four args are passed, they must be:

  * an array of bytes
  * the offset (`Integer`)
  * the length (`Integer`)
  * flags (`Integer`)"
  [args]
  (apply #'otp/create (into [:input-stream] args)))

(defn output-stream
  "Constructor for `OtpOutputStream`.

  If no argument is passed, create a stream with the default initial size
  (2048 bytes).

  If one arg is passed, it must be either:

  * an `Integer` representing the initial size, or
  * an `OtpErlangObject`."
  [& args]
  (apply #'otp/create (into [:output-stream] args)))
