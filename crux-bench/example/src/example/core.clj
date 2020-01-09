(ns example.core
  (:require [clojure.tools.logging :as logging]
            [clojure.data.json :as json]))

(defn -main
  [& args]
  (println (json/write-str {"test" "123"})))
