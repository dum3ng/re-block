(ns re-block.b4.checkbox
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(defn -checkbox
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [inline disabled validation-state
                input-ref class style title]
         :or {inline false disabled false title ""}} props
        props (dissoc props :inline :disabled :validation-state
                      :input-ref :class :style)
        [bsp eps] (u/split-bs-props props)
        input [:input (merge eps
                             {:ref input-ref
                              :type "checkbox"
                              :disabled disabled})]
        clss (uu/class
              class
              (u/get-class-set bsp)
              (uu/assoc-if
               {:disabled disabled} validation-state (str "has-" validation-state) true))]
    (if inline
      (into [:label {:class (uu/class class {:disabled disabled
                                             (u/prefix bsp "inline") true} )
                     :style style
                     :title title} input]
            children)
      
      (into [:div {:style style
                   :class clss}
             [:label {:title title} input]] children)) ))

(def checkbox
  (u/bs-class :checkbox -checkbox))

