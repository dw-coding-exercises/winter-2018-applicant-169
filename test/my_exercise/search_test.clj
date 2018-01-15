(ns my-exercise.search-test
  (:require [clojure.test :refer :all]
            [my-exercise.search :refer :all]))

(deftest format-val-test
  (testing "Formats cities correctly"
    (is (= "place:glen_ellyn"
           (format-val "place:" "city" {"city" "Glen Ellyn"}))))
  (testing "Formats state correctly"
    (is (= "state:il"
           (format-val "state:" "state" {"state" "IL"} )))))

;; TODO Unit test all the helper methods in search.clj

;; TODO Feature test displaying the Elections, once the hiccup templates are made
