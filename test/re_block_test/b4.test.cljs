(ns re-block-test.b4.test
  (:require [cljs.test :as t :refer-macros [deftest testing is]]
            [re-block.b4.utils :as u]
            [clojure.string :as string]))


(deftest utils-test
  (testing "class function"
    (let [c0 "one"
          c1 {:two true
              :three false}
          c2 ["four" "four" "five"]
          cls (string/trim (u/class c0 c1 c2))]
      (is (= cls "one  two four five"))))

  (testing "prefix function"
    (let [bs-props {:bs-class "alert" }
          cls "error"]
      (is (= (u/prefix bs-props) "alert"))
      (is (= (u/prefix bs-props cls) "alert-error"))))

  (testing "get-class-set function"
    (let [props {:bs-class "alert"
                 :bs-size :large
                 :bs-style :primary}
          ]
      (is (= (u/get-class-set props) {"alert" true
                                      "alert-lg" true
                                      "alert-primary" true}))))

  (testing "bs-class function"
    (let [f (fn [p & cs]
              (into  [:div p  ] cs))
          fb (u/bs-class :alert f)
          re (fb {} [:a] [:b])]
      (is (= re [:div {:bs-class :alert} [:a] [:b]])))))

