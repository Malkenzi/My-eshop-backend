CREATE TABLE `shop`.`customer_account` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `customer_id` INT NOT NULL,
  `money` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `customer_id_UNIQUE` (`customer_id` ASC) VISIBLE,
  CONSTRAINT `customer_id`
    FOREIGN KEY (`customer_id`)
    REFERENCES `shop`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);


CREATE TABLE `shop`.`bought_product` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `product_id` INT NOT NULL,
  `customer_id` INT NOT NULL,
  `quantity` INT NOT NULL,
  `bought_at` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `product_id`
    FOREIGN KEY (`product_id`)
    REFERENCES `shop`.`product` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `customer_id_bought`
    FOREIGN KEY (`customer_id`)
    REFERENCES `shop`.`customer` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
