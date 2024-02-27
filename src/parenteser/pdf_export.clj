(ns parenteser.pdf-export
  (:require
   [babashka.fs :as fs]
   [babashka.process :as process]))

;; PDF-eksport av blogg-poster til en mappe
;;
;; Vi kjører via Pandoc. Pandoc har god støtte for HTML. PDF-eksporten skjer via
;; LaTeX, så vi får fin typografi.

(comment
  (def export-root "./docker/build/")
  (def htmlfiles-from-root (fs/glob export-root "**/index.html"))
  (def htmlfiles (map #(fs/relativize export-root %) htmlfiles-from-root))
  (first htmlfiles)

  (def pdf-export-folder (fs/expand-home "~/Nextcloud/log/2024-02/paranteser-pdf-er"))
  pdf-export-folder

  (def process-handle (process/process {:dir export-root}
                                       ["pandoc"
                                        (str (first htmlfiles))
                                        "-o" (str (fs/expand-home (fs/file pdf-export-folder "1.pdf")))]))

  (deref process-handle)
  (slurp (:err (deref process-handle)))
  (first htmlfiles-from-root)

  :rcf)
