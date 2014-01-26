# -*- mode: ruby -*-
# vi: set ft=ruby :

VAGRANTFILE_API_VERSION = "2"

Vagrant.configure(VAGRANTFILE_API_VERSION) do |config|
  config.vm.box = "opscode_ubuntu-12.04_provisionerless"
  config.vm.box_url = "https://opscode-vm-bento.s3.amazonaws.com/vagrant/opscode_ubuntu-12.04_provisionerless.box"

  config.vm.define 'dockerhost' do |c|
    c.vm.hostname = 'dockerhost.demo.devopscasts.com'
    c.vm.network :private_network, ip: "192.168.33.10"
    c.vm.provision :shell, :inline => <<-SH
      sudo /vagrant/provisioning/install docker
    SH
  end
end
