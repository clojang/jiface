(ns jiface.test.otp.nodes-test
  (:require
    [clojure.test :refer :all]
    [jiface.otp.nodes :as nodes]
    [jiface.util :as util]
    [trifl.net :as net])
  (:import
    (com.ericsson.otp.erlang OtpNode OtpPeer)))

(deftest ^:unit node-constructor-test
  (is (= OtpNode
         (type (nodes/node "a"))))
  (is (= OtpNode
         (type (nodes/node "a" "cookie"))))
  (is (= OtpNode
         (type (nodes/node "a" "cookie" 1234)))))

(deftest ^:system node-test
  (let [my-name "mynode"
        hostname (net/get-short-local-hostname)
        my-fullhost (str my-name "@" hostname)
        my-node (nodes/node my-name)]
    (is (= my-fullhost (nodes/->str my-node)))
    (is (= my-fullhost (nodes/get-name my-node)))
    (is (= hostname (nodes/get-hostname my-node)))
    (is (= my-name (nodes/get-alivename my-node)))
    (is (= String (type (nodes/get-cookie my-node))))
    (is (= Integer (type (nodes/get-port my-node))))
    ;; protected
    ; (is (= "" (nodes/get-epmd my-node)))
    ))

(deftest ^:unit peer-constructor-test
  (is (= OtpPeer
         (type (nodes/peer "a")))))
