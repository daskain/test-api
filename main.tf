terraform {
  required_providers {
    yandex = {
      source = "yandex-cloud/yandex"
    }
  }
}
provider "yandex" {
  token     = "AQAAAAA1uF4xAATuwV1LwguCJ0IOsS7F53Jd7YM"
  cloud_id  = "b1gikqk7igq3r6fauv3e"
  folder_id = "b1gn3qkiobqao5rviv11"
  zone      = "ru-central1-a"
}

resource "yandex_compute_instance" "app" {
  name = "reddit-app"

  resources {
    cores  = 2
    memory = 2
  }
	metadata = {
	ssh-keys = "ubuntu:${file("~/.ssh/yc.pub")}"
  }

  boot_disk {
    initialize_params {
      # Указать id образа созданного в предыдущем домашем задании
      image_id = "fd8emgiea78qnr3u65rr"
    }
  }

  network_interface {
    # Указан id подсети default-ru-central1-a
    subnet_id = "e9bveo7053lg65h96cqc"
    nat       = true
  }
  
  connection {
    type  = "ssh"
    host  = self.network_interface.0.nat_ip_address
    user  = "ubuntu"
    agent = false
    # путь до приватного ключа
    private_key = file(var.private_key_path)
  }
}