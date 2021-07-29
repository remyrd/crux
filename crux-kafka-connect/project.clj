(def crux-version (or (System/getenv "CRUX_VERSION") "dev-SNAPSHOT"))

(defproject pro.juxt.crux/crux-kafka-connect "<inherited>"
  :description "Crux Kafka Connect"

  :plugins [[lein-javadoc "0.3.0"]
            [lein-parent "0.3.8"]
            [lein-licenses "0.2.2"]
            [thomasa/mranderson "0.5.1"]]

  :parent-project {:path "../project.clj"
                   :inherit [:version :repositories :deploy-repositories
                             :managed-dependencies
                             :pedantic? :global-vars
                             :license :url :pom-addition]}

  :mranderson {:unresolved-tree true}

  :scm {:dir ".."}

  :dependencies [[org.clojure/clojure "1.10.3"]
                 [pro.juxt.crux/crux-core]
                 [pro.juxt.crux/crux-http-client]
                 [org.clojure/tools.logging "1.1.0"]
                 [pro.juxt.clojars-mirrors.cheshire/cheshire "5.10.0"]
                 [pro.juxt.clojars-mirrors.com.taoensso/nippy "3.1.1"]
                 [com.cognitect/transit-clj "1.0.324"]
                 [org.slf4j/slf4j-api "1.7.30"]]

  :profiles {:provided {:dependencies [[org.apache.kafka/connect-api "2.6.0"]]}}

  :aliases {"package" ["do"
                       ["inline-deps"]
                       ["with-profile" "+plugin.mranderson/config" "uberjar"]
                       ["archive"]]}

  :confluent-hub-manifest {:component_types ["sink" "source"]
                           :description "A Kafka Connect plugin for transferring data between Crux nodes and Kafka. Acts as a source for publishing transacations on a node to a Kafka topic, as well as a sink to receive transactions from a Kafka topic and submit them to a node."
                           :documentation_url "https://github.com/juxt/crux/tree/master/crux-kafka-connect"
                           :features {:confluent_control_center_integration true,
                                      :delivery_guarantee ["exactly_once"],
                                      :kafka_connect_api true,
                                      :single_message_transforms true,
                                      :supported_encodings ["any"]}
                           :license [{:name "The MIT License (MIT)"
                                      :url "https://opensource.org/licenses/MIT"}]
                           :logo "assets/crux-logo.svg"
                           :name "kafka-connect-crux"
                           :owner {:name "JUXT"
                                   :type "organization"
                                   :url "https://juxt.pro/index.html"
                                   :username "juxt"}

                           :title "Kafka Connect Crux"
                           :tags ["Database"
                                  "Crux"]

                           :version ~crux-version}

  :java-source-paths ["src"]
  :javac-options ["-source" "8" "-target" "8"
                  "-Xlint:all,-options,-path"
                  "-Werror"
                  "-proc:none"]

  :javadoc-opts {:package-names ["crux"]
                 :output-dir "target/javadoc/out"
                 :additional-args ["-windowtitle" "Crux Kafka Javadoc"
                                   "-quiet"
                                   "-Xdoclint:none"
                                   "-link" "https://docs.oracle.com/javase/8/docs/api/"
                                   "-link" "https://www.javadoc.io/static/org.clojure/clojure/1.10.3"
                                   "-link" "https://kafka.apache.org/26/javadoc/"]}

  :classifiers {:sources {:prep-tasks ^:replace []}
                :javadoc {:prep-tasks ^:replace ["javadoc"]
                          :omit-source true
                          :filespecs ^:replace [{:type :path, :path "target/javadoc/out"}]}})
