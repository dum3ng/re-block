(ns re-block-test.b4.test
  (:require [cljs.test :as t :refer-macros [deftest testing is]]
            [re-block.b4.utils :as u]
            [re-block.utils :as uu]
            [re-block.b4.grid :as grid]
            [clojure.string :as string]))


(deftest utils-test
  (testing "class function"
    (let [c0 "one"
          c1 {:two true
              :three false}
          c2 ["four" "four" "five"]
          cls (string/trim (uu/class c0 c1 c2))]
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
      (is (= re [:div {:bs-class :alert} [:a] [:b]]))))

  (testing "grid testing"
    (let [r0 (grid/prefix-align :align-items :center)
          r1 (grid/prefix-align :align-items :flex-start
                               :justify-content "center")]
      (is (= r0 "align-items-center"))
      (is (= r1 "align-items-flex-start justify-content-center")))

    (let [r (grid/prefix-flex {:xs 3
                               :sm "auto"
                               :lg 4
                               :offset-sm 3} "col")]
      (is (= (-> r string/trim
                 (string/split #"\s+")
                 set)
             (set (string/split "col-xs-3 col-sm-auto col-lg-4 offset-sm-3" #" ")))))))

