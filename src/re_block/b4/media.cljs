(ns re-block.b4.media
  (:require [re-block.utils :as uu]
            [re-block.b4.utils :as u]))

(declare media-body media-left media-right media-heading
         media-list-item media-list)

(defn -media
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [tag class] :or {tag "div"}} props
        props (dissoc props :class :tag )
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              class (u/get-class-set bsp))]
    (if (fn? tag )
      (apply tag (merge eps {:class clss}) children)
      (into [(keyword tag) (merge eps {:class clss})]
            children))))

(def media
  (with-meta
    (u/bs-class "media" -media)
    {:display-name "Media"}))

(defn -media-body
  [props & children ]
  (let [[props children] (if (map? props)
                           [props children]
                           [{} (into [props]  children)])
        {:keys [class tag]
         :or {tag "div"}} props
        props (dissoc props :class )
        [bsp eps] (u/split-bs-props props)
        clss (uu/class
              class (u/get-class-set bsp))]
    (if (fn? tag )
      (apply tag (merge eps {:class clss}) children)
      (into [(keyword tag) (merge eps {:class clss})]
            children))))

(def media-body
  (with-meta
    (u/bs-class "media-body" -media-body)
    {:display-name "MediaBody"}))


;; ;; media-left
;; (defn -media-left
;;   [props & children ]
;;   (let [[props children] (if (map? props)
;;                            [props children]
;;                            [{} (into [props]  children)])
;;         {:keys [align class] } props
;;         props (dissoc props :align :class )
;;         [bsp eps] (u/split-bs-props props)
;;         clss (uu/class
;;               class (-> (u/get-class-set bsp)
;;                         (assoc-if align
;;                                   (u/prefix {:bs-class "media-top"} align ) true)) )]
;;     (into [:div (merge eps {:class clss})] children)))

;; (def media-left
;;   (u/bs-class "media-left" -media-left))


;; ;; media-right
;; (defn -media-right
;;   [props & children ]
;;   (let [[props children] (if (map? props)
;;                            [props children]
;;                            [{} (into [props]  children)])
;;         {:keys [align class] } props
;;         props (dissoc props :align :class )
;;         [bsp eps] (u/split-bs-props props)
;;         clss (uu/class
;;               class (-> (u/get-class-set bsp)
;;                         (assoc-if align
;;                                   (u/prefix {:bs-class "media-top"} align ) true)) )]
;;     (into [:div (merge eps {:class clss})] children)))

;; (def media-right
;;   (u/bs-class "media-left" -media-right))



