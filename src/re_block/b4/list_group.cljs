(ns re-block.b4.list-group
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]
            [re-block.b4.list-group-item :refer [list-group-item]]))


(defn- default-tag
  [children]
  (cond
    (not children) "div"
    (some #(let [ps (uu/reagent-props %)
                 v (first %)
                 _type (-> v meta :display-name)]
             (or (not= _type "ListGroupItem")
                 (:href ps)
                 (:on-click ps))) children) "div"
    :else "ul"
    ))

(defn -list-group
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [class] } props
        props (dissoc props :class )
        tag (default-tag children)
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              class
              (u/get-class-set bsp))
        use-list-item (and (= "ul" tag)
                           (every? #(= "ListGroupItem"
                                       (-> % first meta :display-name))
                            children))]
    (into [(keyword tag) (merge eps {:class clss})]
          (if use-list-item
            (mapv #(uu/clone-element % {:list-item true})
                  children) 
            children))))

(def list-group
  (with-meta
    (u/bs-class "list-group" -list-group)
    {:display-name "ListGroup"}))


