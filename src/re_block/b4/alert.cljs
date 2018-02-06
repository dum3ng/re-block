(ns re-block.b4.alert
  (:require [re-block.b4.close-button :refer [close-button]]
            [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(defn _alert
  [props & children]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [on-dismiss close-label class] :or {close-label "Close alert"}  } props
        props (dissoc props :on-dismiss :close-label :class)
        [bs-props eps] (u/split-bs-props props)
        dismiss? (not (not on-dismiss))
        clss (uu/class
              class
              (u/get-class-set bs-props)
              {(u/prefix bs-props "dismissable") dismiss?}) ]
    (into [:div (merge eps {:role "alert"
                           :class clss})
           (if on-dismiss
             [close-button {:on-click #(on-dismiss)
                            :label close-label}])]
          children)) )

(def alert
  (u/bs-style :info (u/bs-class :alert _alert)))
