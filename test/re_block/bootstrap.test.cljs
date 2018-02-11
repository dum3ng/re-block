(ns re-block.bootstrap.test
  (:require  [cljs.test :as t :include-macros true
              :refer-macros [deftest testing is]]
             [clojure.string :as string]
             [re-block.bootstrap.utils :as u]
             [re-block.bootstrap.grid :as grid]))


(deftest utils-test
  (testing "destruct test"
    (let [[p c] (u/destruct {:a 3 :b 4} )
          [p1 c1] (u/destruct {:a 3} ["hello"])
          [p2 c2] (u/destruct [:a 3] [[:div] [:a 3]])]
      (is (and (= p {:a 3 :b 4})
               (= c nil)))
      (is (and (= p1 {:a 3})
               (= c1 ["hello"])))
      (is (= p2 {} ))
      (is (= c2 [[:a 3] [:div] [:a 3]])))
    (testing "prefix test"
      (let [base "btn"
          fixers {:size "lg"
                  :color :primary
                  :block true}
          re (u/prefix base fixers)]
        (is (= re ["btn-lg" "btn-primary" "btn-block"])))
      (let [base "btn"
            f 3
            re (u/prefix base f)]
        (is (= re "btn-3"))))
    ))

(defn make-set
  [s]
  (-> s string/trim
      (string/split #"\s+")
      set))

(deftest grid-test
  (testing "col class test"
    (let [props {:xs 1 :sm 2
                 :offset 3
                 :order 2
                 :size 3
                 :offset-sm 2
                 :offset-xs 4}
          clss (grid/col-class props)]
      (is (= (make-set clss)
             (make-set "col-3 order-2 col-sm-2 offset-sm-2 offset-3"))))))
