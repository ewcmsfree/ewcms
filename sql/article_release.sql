ALTER TABLE doc_article_main
  ADD CONSTRAINT doc_article_main_channel_id_fkey FOREIGN KEY (channel_id)
      REFERENCES site_channel (id) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION;