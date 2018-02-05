(ns re-block-test.scrollbars.test
  (:require [cljs.test :as t :include-macros true :refer-macros [deftest is testing]]
            [re-block.scrollbars.utils :as u :refer [css]]))


(deftest scrollbars-utils-test
  (testing "css test"
    (let [div (js/document.createElement "div")
          cssed (css div {:width "10px"
                          :user-select "none"})]
      (is (= "10px" (aget cssed "style" "width"))))))
