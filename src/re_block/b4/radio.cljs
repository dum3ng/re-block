(ns re-block.b4.radio
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(defn -radio
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [inline disabled validation-state
                input-ref class style title]
         :or {inline false disabled false title ""}} props
        props (dissoc props )
        [bsp eps] (u/split-bs-props props)
        input [:input (merge eps  {:ref input-ref
                                   :type "radio"
                                   :disabled disabled})]
        clss (uu/class
              )]
    (if inline
      (into
       [:label {:style style
                :title title
                :class (uu/class class
                                 {:disabled disabled
                                  (u/prefix bsp "inline") true})} input] children)
      (into
       [:div {:style style
              :class (uu/class class
                               (merge {:disabled disabled}
                                      (u/get-class-set bsp)))}
        input]
       children))))

(def radio
  (u/bs-class "radio"  -radio))

