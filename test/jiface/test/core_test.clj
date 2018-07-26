(ns ^:unit jiface.test.core-test
  (:require
    [clojure.test :refer :all]
    [jiface.core :as jiface])
  (:import
    [com.ericsson.otp.erlang]))

(deftest name->class-name-test
  (is (= "Atom" (jiface/name->class-name :atom)))
  (is (= "Boolean" (jiface/name->class-name :boolean)))
  (is (= "List" (jiface/name->class-name :list)))
  (is (= "ExternalFun" (jiface/name->class-name :external-fun)))
  (is (= "List$SubList" (jiface/name->class-name :sublist)))
  (is (= "Object$Hash" (jiface/name->class-name :object-hash)))
  (is (= "UInt" (jiface/name->class-name :uint)))
  (is (= "UShort" (jiface/name->class-name :ushort)))
  (is (= "LocalNode" (jiface/name->class-name :local-node))))

(deftest name->class-name-test
  (is (= 'com.ericsson.otp.erlang.OtpErlangAtom
         (jiface/name->class :erlang :atom)))
  (is (= 'com.ericsson.otp.erlang.OtpErlangBoolean
         (jiface/name->class :erlang :boolean)))
  (is (= 'com.ericsson.otp.erlang.OtpErlangList
         (jiface/name->class :erlang :list)))
  (is (= 'com.ericsson.otp.erlang.OtpErlangExternalFun
         (jiface/name->class :erlang :external-fun)))
  (is (= 'com.ericsson.otp.erlang.OtpErlangList$SubList
         (jiface/name->class :erlang :sublist)))
  (is (= 'com.ericsson.otp.erlang.OtpErlangObject$Hash
         (jiface/name->class :erlang :object-hash)))
  (is (= 'com.ericsson.otp.erlang.OtpErlangUInt
         (jiface/name->class :erlang :uint)))
  (is (= 'com.ericsson.otp.erlang.OtpErlangUShort
         (jiface/name->class :erlang :ushort)))
  (is (= 'com.ericsson.otp.erlang.OtpLocalNode
         (jiface/name->class :otp :local-node))))

(deftest dynamic-init
  (is (= (resolve 'com.ericsson.otp.erlang.OtpErlangAtom)
          (type (jiface/dynamic-init :erlang [:atom "a"]))))
  (is (= (resolve 'com.ericsson.otp.erlang.OtpNode)
          (type (jiface/dynamic-init :otp [:node "a"])))))
