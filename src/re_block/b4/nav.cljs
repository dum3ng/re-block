(ns re-block.b4.nav)

;; (defn nav-item
;;   []
;; [:]  )

(defn navs
  "item is a map, with a key :children, now support `string`"
  [& items]
  (let [items-ui (for [item items]
                   [:li.nav-item
                    [:a.nav-link
                     (merge {:href "#"} (dissoc  item :children))
                     (:children item)]])]
    (into [:ul.nav] items-ui)))


;; (defn navbar
;;   []
;;   [:nav.navbar.navbar])

