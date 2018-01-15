(ns my-exercise.search
  (:require [clojure.string :as st]))

(defonce ocd-base "ocd-division/country:us")

(defn submit [request]
  (html5 (str (ocd-ids (form-params request)))))

(defn form-params [request]
  (get request :form-params))


(defn format-val [prepend getter address]
  (str prepend (st/lower-case (get address getter))) )

(def get-state
  (partial format-val "state:" "state"))

(def get-place
  (partial format-val "place:" "city"))

(defn ocd-ids [address]
  [ocd-base
   (st/join "/" [ocd-base (get-state address)])
   (st/join "/" [ocd-base (get-place address)])])



;; ocd-division/country:us
;; ocd-division/country:us/state:or
;; ocd-division/country:us/state:or/county:multnomah
;; ocd-division/country:us/state:or/place:portland 
