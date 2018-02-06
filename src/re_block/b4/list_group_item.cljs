(ns re-block.b4.list-group-item
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(defn- render-header
  [header cls]
  (if (vector? header)
    (uu/clone-element header
                      {:class (uu/class (uu/reagent-props header)
                                        cls)})
    [:h4 {:class cls} header]))

(defn -list-group-item
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [active disabled class
                header list-item ]
         :or {list-item false}} props
        props (dissoc props :active :disabled :class
                      :header :list-item)
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              class
              (u/get-class-set bsp)
              {:active active
               :disabled disabled})
        eps (assoc eps :class clss)
        [tag eps] (cond
                    (:href eps) ["a" eps]
                    (:on-click eps) ["button" (assoc eps :type (or (:type eps) "button"))]
                    list-item ["li" eps]
                    :else ["span" eps])]
    (if header
      (into [(keyword tag) eps
             (render-header header (u/prefix bsp "heading"))
             [:p {:class (u/prefix bsp "text")}]]
            children)
      (into [(keyword tag) eps] children)) ))

(def list-group-item
  (with-meta
    (u/bs-class "list-group-item" -list-group-item)
    {:display-name "ListGroupItem"}))

