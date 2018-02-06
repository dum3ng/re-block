(ns re-block.b4.alert
  (:require [re-block.b4.close-button :refer [close-button]]
            [re-block.b4.utils :as u]))

(defn _alert
  [{:keys [on-dismiss close-label] :or {close-label "Close alert"} :as props} & children]
  (let [[bs-props eps] (u/split-bs-props props)
        dismiss? (not (not on-dismiss))
        clss (u/class
              (u/get-class-set bs-props)
              {(keyword (u/prefix bs-props "dismissable")) dismiss?}) ]
    (into [:div (merge eps {:role "alert"
                           :class clss})
           (if on-dismiss
             [close-button {:on-click #(on-dismiss)
                            :label close-label}])]
          children)) )

(def alert
  (u/bs-style :info (u/bs-class :alert _alert)))
