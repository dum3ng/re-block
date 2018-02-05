(ns re-block.scrollbars.core
  (:require [reagent.core :as r]
            [re-block.scrollbars.utils :as u :refer [css ]]
            [clojure.string :as string]
            [re-block.utils :as uu :refer [$ $! ]]
            [re-block.scrollbars.default-render-elements
             :refer [render-view-default
                     render-track-horizontal-default
                     render-track-vertical-default
                     render-thumb-horizontal-default
                     render-thumb-vertical-default]]
            [re-block.scrollbars.styles :as styles]))

(defn- js-merge
  [& os]
  (apply js/Object.assign   #js{} os ))

(defn- react-style
  [style]
  (let [s ()]))


(defn- clone-element
  " the view is a vector with
  [:tag props  & children]"
  ([view props]
   (clone-element view props nil))
  (  [view props children]
   (let [[tag ps & old] view
         props (merge ps props)]
     (if children
       (if (not (vector? (first children))) ; single child
         [tag props children]
         (into [tag props] children))
       (into [tag props ] old)))))
;; (defn create-element
;;   [tag p children]
;;   (.createElement js/React tag p children))

;; (defn clone-element
;;   [tag p children]
;;   (.cloneElement js/React tag p ))


(defn- get-scroll
  [p c]
  (if (not (.-view c))
    0
    (aget c "view" (str "scroll" (string/capitalize (name p))))))

(defn- get-client
  [p c]
  (if (not (.-view c))
    0
    (aget c "view" (str "client" (string/capitalize (name p))))))

(defn- get-values
  [c]
  (let [view (or (.-view c) #js{})
        scroll-left (or (.-scrollLeft view) 0)
        scroll-top (or (.-scrollTop view) 0)
        scroll-width (or (.-scrollWidth view) 0)
        scroll-height (or (.-scrollHeight view) 0)
        client-width (or (.-clientWidth view) 0)
        client-height (or (.-clientHeight view) 0)
        ]
    {:left (or (/ scroll-left (- scroll-width client-width)) 0)
     :top (or (/ scroll-top (- scroll-height client-height)) 0)
     :scroll-left scroll-left
     :scroll-top scroll-top
     :scroll-width scroll-width
     :scroll-height scroll-height
     :client-width client-width
     :client-height client-height}))

(defn- get-thumb-horizontal-width
  [c]
  (let [{:keys [thumb-size thumb-min-size]} (r/props c)
        scroll-width (.. c -view -scrollWidth)
        client-width (.. c -view -clientWidth)
        track-width (u/get-inner-width (. c -trackHorizontal))
        width (js/Math.ceil (* track-width (/ client-width scroll-width)))]
    (cond
      (= width track-width) 0
      (not (not thumb-size)) thumb-size
      :else (js/Math.max width thumb-min-size))))

(defn- get-thumb-vertical-height
  [c]
  (let [{:keys [thumb-size thumb-min-size]} (r/props c)
        scroll-height (.. c -view -scrollHeight)
        client-height (.. c -view -clientHeight)
        track-height (u/get-inner-height (. c -trackVertical))
        height (js/Math.ceil (* track-height (/ client-height scroll-height)))]
    (cond
      (= height track-height) 0
      (not (not thumb-size)) thumb-size
      :else (js/Math.max height thumb-min-size))))

(defn- get-scroll-left-for-offset
  [c offset]
  (let [view (. c -view)
        {:keys [scrollWidth clientWidth]} (uu/$$ view :scrollWidth :clientWidth)
        track-width (u/get-inner-width (. c -trackHorizontal))
        thumb-width (get-thumb-horizontal-width c)]
    (-> offset
        (/ (- track-width thumb-width))
        (* (- scrollWidth clientWidth)))))

(defn- get-scroll-top-for-offset
  [c offset]
  (let [view (. c -view)
        {:keys [scrollHeight clientHeight]} (uu/$$ view :scrollHeight :clientHeight)
        track-height (u/get-inner-height (. c -trackVertical))
        thumb-height (get-thumb-vertical-height c)]
    (-> offset
        (/ (- track-height thumb-height))
        (* (- scrollHeight clientHeight)))))

(defn- scroll-left
  ([c left]
   (if (not (. c -view))
     nil
     (aset c "view" "scrollLeft" left)))
  ([c ]
   (scroll-left c 0)))

(defn- scroll-top
  ([c top]
   (if (not (. c -view))
     nil
     (aset c "view" "scrollTop" top)))
  ([c]
   (scroll-top c 0)))

(defn- scroll-to
  [c dir]
  (if (not (. c -view))
    nil
    (case dir
      :left (aset c "view" "scrollLeft" 0)
      :top (aset c "view" "scrollTop" 0)
      :right (aset c "view" "scrollLeft"
                   (.. c -view -scrollWidth))
      :bottom (aset c "view" "scrollTop"
                    (.. c -view -scrollHeight)))))

(defn- add-listeners
  [c]
  (if (or (nil? js/document)
          (not (. c -view)))
    nil
    (let [{:keys [view trackHorizontal trackVertical thumbHorizontal thumbVertical]} (uu/$$ c :view :trackHorizontal :trackVertical :thumbHorizontal :thumbVertical)
          view (doto view
                 (.addEventListener "click" #(print "view clicked " (rand-int 10) ))
                 (.addEventListener "scroll" ($ c :handleScroll)))]
      (if (not (u/get-scrollbar-width))
        nil
        (do
          (doto trackHorizontal
            (.addEventListener "mouseenter" ($ c :handleTrackMouseEnter))
            (.addEventListener "mouseleave" ($ c :handleTrackMouseLeave))
            (.addEventListener "mousedown" ($ c :handleHorizontalTrackMouseDown)))
          (doto trackVertical
            (.addEventListener "mouseenter" ($ c :handleTrackMouseEnter))
            (.addEventListener "mouseleave" ($ c :handleTrackMouseLeave))
            (.addEventListener "mousedown" ($ c :handleVerticalTrackMouseDown)))
          (doto thumbHorizontal
            (.addEventListener "mousedown" ($ c :handleHorizontalThumbMouseDown)))
          (doto thumbVertical
            (.addEventListener "mousedown" ($ c :handleVerticalThumbMouseDown)))
          (.addEventListener js/window "resize" ($ c :handleWindowResize))))
      )))

(defn- remove-listeners
  [c]
  (if (or (nil? js/document)
          (not (. c -view)))
    nil
    (let [{:keys [view trackHorizontal trackVertical thumbHorizontal thumbVertical]} (uu/$$ c  :view :trackHorizontal :trackVertical :thumbHorizontal :thumbVertical)
          view (doto view
                 (.removeEventListener "scroll" ($ c  :handleScroll)))]
      (if (not (u/get-scrollbar-width))
        nil
        (do
          (doto trackHorizontal
            (.removeEventListener "mouseenter" ($ c  :handleTrackMouseEnter))
            (.removeEventListener "mouseleave" ($ c  :handleTrackMouseLeave))
            (.removeEventListener "mousedown" ($ c  :handleHorizontalTrackMouseDown)))
          (doto trackVertical
            (.removeEventListener "mouseenter" ($ c  :handleTrackMouseEnter))
            (.removeEventListener "mouseleave" ($ c  :handleTrackMouseLeave))
            (.removeEventListener "mousedown" ($ c  :handleVerticalTrackMouseDown)))
          (doto thumbHorizontal
            (.removeEventListener "mousedown" ($ c  :handleHorizontalThumbMouseDown)))
          (doto thumbVertical
            (.removeEventListener "mousedown" ($ c  :handleVerticalThumbMouseDown)))
          (.removeEventListener js/window "resize" ($ c  :handleWindowResize))))
      )))


(js-comment
  "only event listener need to bind this, so
 to use `(this-as c)` and autobind the function to this.
While other functions do not need to bind this,
 they need a referenc to `this`, we pass `this` as the first
parameter to make it accessable .")

(def _scrollbars
  (r/create-class
   {:handleScroll
    (fn [e]
      (this-as c
        (print "scroll....")
        (let [{:keys [on-scroll on-scroll-frame]}  (r/props c)]
          (if on-scroll
            (on-scroll e))
          (.update c c
                   #(let [{:keys [scroll-left scroll-top]} %]
                      (aset c "viewScrollLeft" scroll-left)
                      (aset c "viewScrollTop" scroll-top)
                      (if on-scroll-frame
                        (on-scroll-frame %))
                      ))
          (.detectScrolling c c))))

    :handleScrollStart
    (fn [c]
      (let [{:keys [on-scroll-start]} (r/props c)]
           (if on-scroll-start
             (on-scroll-start))
           (.handleScrollStartAutoHide c c)))

    :handleScrollStartAutoHide
    (fn [c ]
      (let [{:keys [auto-hide]} (r/props c)]
        (if auto-hide
          (.showTracks c c)))     )

    :handleScrollStop
    (fn [c]
      (let [{:keys [on-scroll-stop]} (r/props c)]
           (if on-scroll-stop 
             (on-scroll-stop))
           (.handleScrollStopAutoHide c c)))

    :handleScrollStopAutoHide
    (fn [c]
      (let [{:keys [auto-hide]} (r/props c)]
           (if auto-hide
             (.hideTracks c c))))

    :handleWindowResize
    (fn []
      (this-as c
        (.update c c)))

    :handleHorizontalTrackMouseDown
    (fn [e]
      (this-as c
        (.preventDefault e)
        (let [target ($ e :target)
              clientX ($ e :clientX)
              targetLeft ($ (.getBoundingClientRect target) :left)
              thumbWidth (get-thumb-horizontal-width c)
              offset (- (js/Math.abs (- targetLeft clientX))
                        (/ thumbWidth 2))]
          (aset c "view" "scrollLeft" (get-scroll-left-for-offset c offset)))))

    :handleVerticalTrackMouseDown
    (fn [e]
      (this-as c
        (.preventDefault e)
        (let [target ($ e :target)
              clientY ($ e :clientY)
              targetTop ($ (.getBoundingClientRect target) :top)
              thumbHeight (get-thumb-vertical-height c)
              offset (- (js/Math.abs (- targetTop clientY))
                        (/ thumbHeight 2))]
          (aset c "view" "scrollTop" (get-scroll-top-for-offset c offset)))) )

    :handleHorizontalThumbMouseDown
    (fn [e]
      (this-as c
        (.preventDefault e)
        (.handleDragStart c c e)
        (let [target ($ e :target)
              clientX ($ e :clientX)
              offsetWidth ($ target :offsetWidth)
              left ($ (.getBoundingClientRect target) :left)]
          (aset c "prevPageX" (- offsetWidth
                                 (- clientX left))))))

    :handleVerticalThumbMouseDown
    (fn [e]
      (this-as c
        (.preventDefault e)
        (.handleDragStart c c e)
        (let [target ($ e :target)
              clientY ($ e :clientY)
              offsetHeight ($ target :offsetHeight)
              top ($ (.getBoundingClientRect target) :top)]
          (aset c "prevPageY" (- offsetHeight
                                 (- clientY top))))))

    :setupDragging
    (fn [c]
      (css js/document.body styles/disable-select-style)
      (doto js/document
        (.addEventListener "mousemove" ($ c :handleDrag))
        (.addEventListener "mouseup" ($ c :handleDragEnd))
        (aset "onselectstart" (constantly false))))

    :teardownDragging
    (fn [c]
      (css js/document.body styles/disable-select-style)
      (doto js/document
        (.removeEventListener "mousemove" ($ c :handleDrag))
        (.removeEventListener "mouseup" ($ c :handleDragEnd))
        (aset "onselectstart" nil)))

    :handleDragStart
    (fn [ c e]
      (aset c "dragging" true)
      (.stopImmediatePropagation e)
      (.setupDragging c c))

    :handleDrag
    (fn [e]
      (this-as c
        (if ($ c :prevPageX)
          (let [clientX ($ e :clientX)
                trackLeft ($ (.. c -trackHorizontal getBoundingClientRect) :left)
                thumbWidth (get-thumb-horizontal-width c)
                clickPosition (- thumbWidth ($ c :prevPageX))
                offset (-> clientX
                           (- trackLeft)
                           (- clickPosition))]
            (aset c "view" "scrollLeft" (get-scroll-left-for-offset c offset))))
        (if ($ c :prevPageY)
          (let [clientY ($ e :clientY)
                trackTop ($ (.. c -trackHorizontal getBoundingClientRect) :top)
                thumbHeight (get-thumb-vertical-height c)
                clickPosition (- thumbHeight ($ c :prevPageY))
                offset (-> clientY
                           (- trackTop)
                           (- clickPosition))]
            (aset c "view" "scrollTop" (get-scroll-top-for-offset c offset))))
        false))

    :handleDragEnd
    (fn []
      (this-as c
        (doto c
          (aset "dragging" false)
          (aset "prevPageY" 0)
          (aset "prevPageX" 0)
          (.teardownDragging c c)
          (.handleDragEndAutoHide c c))))

    :handleDragEndAutoHide
    (fn [c]
      (let [{auto-hide :auto-hide} (r/props c)]
        (if auto-hide
          (.hideTracks c c)))     )

    :handleTrackMouseEnter
    (fn []
      (this-as c
        (doto c
          (aset "trackMouseOver" true)
          (.handleTrackMouseEnterAutoHide c))))

    :handleTrackMouseEnterAutoHide
    (fn [c]
      (let [{auto-hide :auto-hide} (r/props c)]
        (if auto-hide
          (.showTracks c c))))

    :handleTrackMouseLeave
    (fn []
      (this-as c
        (doto c
          (aset "trackMouseOver" false)
          (.handleTrackMouseLeaveAutoHide c c))))

    :handleTrackMouseLeaveAutoHide
    (fn [c]
      (let [{auto-hide :auto-hide} (r/props c)]
        (if auto-hide
          (.hideTracks c c))))

    :showTracks
    (fn [c]
      (js/clearTimeout ($ c :hideTracksTimeout))
      (css ($ c :trackHorizontal ) {:opacity 1})
      (css ($ c :trackVertical ) {:opacity 1})     )

    :hideTracks
    (fn [c]
      (if (or ($ c :dragging)
              ($ c :scrolling)
              ($ c :trackMouseOver))
        nil
        (let [t (:auto-hide-timeout (r/props c))]
          (js/clearTimeout ($ c :hideTracksTimeout))
          (aset c "hideTracksTimeout"
                (js/setTimeout
                 #(do
                    (css ($ c :trackHorizontal) {:opacity 0} )
                    (css ($ c :trackVertical) {:opacity 0} )) t))))     )

    :detectScrolling
    (fn [c]
      (if (not ($ c :scrolling))
        (doto c
          ($! :scrolling true)
          (.handleScrollStart c c)
          ($! :detectScrollingInterval
              (js/setInterval
               (fn []
                 (if (and (= ($ c :lastViewScrollLeft)
                             ($ c :viewScrollLeft))
                          (= ($ c :lastViewScrollTop)
                             ($ c :viewScrollTop)))
                   (do
                     (js/clearInterval ($ c :detectScrollingInterval))
                     ($! c :scrolling false)
                     (.handleScrollStop c c)))
                 (doto c
                   ($! :lastViewScrollLeft ($ c :viewScrollLeft))
                   ($! :lastViewScrollTop ($ c :viewScrollTop))))
               100)))))

    :raf
    (fn [c cb]
      (if ($ c :requestFrame)
        (js/cancelAnimationFrame ($ c :requestFrame)))
      ($! c :requestFrame
          (js/requestAnimationFrame
           (fn []
             ($! c :requestFrame nil)
             (cb))))     )

    :update
    (fn [ c cb]
      (.raf c c (fn []
                  (._update c c cb)))     )

    :_update
    (fn [c cb]
      (let [{:keys [on-update hide-tracks-when-not-needed]} (r/props c)
            values (get-values c)]
        (if (u/get-scrollbar-width)
          (let [{:keys [scroll-left client-width scroll-width
                        scroll-top client-height scroll-height]} values
                track-hw (u/get-inner-width ($ c :trackHorizontal))
                thumb-hw (get-thumb-horizontal-width c)
                thumb-hx (-> scroll-left
                            (/ (- scroll-width client-width))
                            (* (- track-hw thumb-hw)))
                thumb-hs {:width thumb-hw
                         :transform (str "translateX(" thumb-hx "px)" )}
                track-vh (u/get-inner-height ($ c :trackVertical))
                thumb-vh (get-thumb-vertical-height c)
                thumb-vy(-> scroll-top
                            (/ (- scroll-height client-height))
                            (* (- track-vh thumb-vh)))
                thumb-vs {:height thumb-vh
                         :transform (str "translateY(" thumb-vy"px)")}]
            (print scroll-left client-width scroll-width
                   scroll-top client-height scroll-height)
            (print "track-hw" track-hw)
            (print "track v h" track-vh)
            (print "thumb vs" thumb-vs)
            (if hide-tracks-when-not-needed
              (let [track-hs {:visibility (if (> scroll-width client-width) "visible" "hidden")}
                    track-vs {:visibility (if (> scroll-height client-height) "visible" "hidden")}]
                (css ($ c :trackHorizontal) track-hs)
                (css ($ c :trackVertical) track-vs)))
            (css ($ c :thumbHorizontal) thumb-hs)
            (css ($ c :thumbVertical) thumb-vs)))
        (if on-update (on-update values))
        (if (fn? cb)
          (cb values)))     )

    :render
    (fn [this]
      (let [scrollbar-width (u/get-scrollbar-width)
            {:keys [on-scroll
                    on-scroll-frame
                    on-scroll-start
                    on-scroll-stop
                    on-update
                    render-view
                    render-track-horizontal
                    render-track-vertical
                    render-thumb-horizontal
                    render-thumb-vertical
                    tag-name
                    hide-tracks-when-not-needed
                    auto-hide
                    auto-hide-timeout
                    auto-hide-duration
                    thumb-size
                    thumb-min-size
                    universal
                    auto-height
                    auto-height-min
                    auto-height-max
                    style]} (r/props this)
            props (dissoc (r/props this)
                          :on-scroll
                          :on-scroll-frame
                          :on-scroll-start
                          :on-scroll-stop
                          :on-update
                          :render-view
                          :render-track-horizontal
                          :render-track-vertical
                          :render-thumb-horizontal
                          :render-thumb-vertical
                          :tag-name
                          :hide-tracks-when-not-needed
                          :auto-hide
                          :auto-hide-timeout
                          :auto-hide-duration
                          :thumb-size
                          :thumb-min-size
                          :universal
                          :auto-height
                          :auto-height-min
                          :auto-height-max
                          :style)
            children (r/children this)
            {:keys [did-mount-universal]} (r/state this)
            container-style (merge styles/container-style-default
                                   (if auto-height (merge styles/container-style-auto-height {:min-height auto-height-min
                                                                                              :max-height auto-height-max})) style)
            view-style (merge styles/view-style-default
                              {:margin-right (if scrollbar-width (- 0 scrollbar-width) 0)
                               :margin-bottom (if scrollbar-width (- 0 scrollbar-width) 0)}
                              (if auto-height (merge styles/view-style-auto-height {:min-height (if (string? auto-height-min)
                                                                                                  (str "calc(" auto-height-min "+" scrollbar-width "px)")
                                                                                                  (+ auto-height-min scrollbar-width))
                                                                                    :max-height (if (string? auto-height-max)
                                                                                                  (str "calc(" auto-height-max "+" scrollbar-width "px)")
                                                                                                  (+ auto-height-max scrollbar-width))}))
                              (if (and auto-height universal (not did-mount-universal))
                                {:min-height auto-height-min
                                 :max-height auto-height-max})
                              (if (and universal (not did-mount-universal)) styles/view-style-universal-initial))
            track-auto-height-style {:transition (str "opacity " auto-hide-duration "ms")
                                     :opacity 0}
            track-horizontal-style (merge styles/track-horizontal-style-default (if auto-hide track-auto-height-style)
                                          (if (or (not scrollbar-width)
                                                  (and universal (not did-mount-universal)))
                                            {:display "none"}))
            track-vertical-style (merge
                                  styles/track-vertical-style-default
                                  (if auto-hide track-auto-height-style)
                                  (if (or (not scrollbar-width)
                                          (and universal (not did-mount-universal)))
                                    {:display "none"}))]
        [(keyword tag-name) (merge props {:style container-style
                                          :ref #(aset this "container" %)})
         (clone-element (render-view {:style view-style})
                        {:key "view"
                         :ref #(aset this "view" %)}
                        children)
         (clone-element (render-track-horizontal {:style track-horizontal-style})
                {:key "trackHorizontal"
                 :ref #(aset this "trackHorizontal" %)}
                (clone-element (render-thumb-horizontal {:style styles/thumb-horizontal-style-default})
                       {:ref #(aset this "thumbHorizontal" %)}))
         (clone-element (render-track-vertical {:style track-vertical-style})
                {:key "trackVertical"
                 :ref #(aset this "trackVertical" %)}
                (clone-element (render-thumb-vertical {:style styles/thumb-vertical-style-default})
                       {:ref #(aset this "thumbVertical" %)}))]
        ))

    :get-initial-state
    (fn [this]
      {:did-mount-universtal false})

    :component-did-mount
    (fn [this]
      (add-listeners this )
      (.update this this)
      (.componentDidMountUniversal this this))

    :componentDidMountUniversal
    (fn [c]
      (let [{universal :universal} (r/props c)]
        (if universal
          (r/set-state c {:did-mount-universtal true}))))

    :component-did-update
    (fn [this]
      (.update this this))

    :component-will-unmount
    (fn [this]
      (remove-listeners this)
      (js/cancelAnimationFrame ($ this :requestFrame))
      (js/clearTimeout ($ this :hideTracksTimeout))
      (js/clearInterval($ this :detectScrollingInterval)))
    }))

(defn scrollbars-bind
  []
  (uu/autobind _scrollbars

              :handleScroll
              :handleDrag
              :handleDragEnd
              :handleTrackMouseEnter
              :handleTrackMouseLeave
              :handleHorizontalTrackMouseDown
              :handleVerticalTrackMouseDown
              :handleHorizontalThumbMouseDown
              :handleVerticalThumbMouseDown
              :handleWindowResize))

(defn scrollbars
  [props & children]
  (into [scrollbars-bind (merge props
                            {:render-view render-view-default
                             :render-track-horizontal render-track-horizontal-default
                             :render-track-vertical render-track-vertical-default
                             :render-thumb-horizontal render-thumb-horizontal-default
                             :render-thumb-vertical render-thumb-vertical-default
                             :tag-name "div"
                             :thumb-min-size 30
                             :hide-tracks-when-not-needed false
                             :auto-hide false
                             :auto-hide-timeout 1000
                             :auto-hide-duration 200
                             :auto-height false
                             :auto-height-min 0
                             :auto-height-max 200
                             :universal false}) ] children))
