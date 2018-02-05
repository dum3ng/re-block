(ns re-block-test.utils.test
  (:require [cljs.test :as t :include-macros true :refer-macros [deftest is testing]]
            [re-block.utils :as u]))


(deftest utils-test
  (testing "$ test"
    (let [el (clj->js {:a {:aa 3 :ab 4} :b 5 :c 6})
          v (u/$ el :b)
          vv (u/$$ el :b :c)]
      (is (= 5 v))

      (is (= vv {:b 5 :c 6}))))

  (testing "dash-camel test"
    (let [s :one-two-three
          c (u/dash->camel s)]
      (is (= c "oneTwoThree")))))
