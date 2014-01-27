Vagrant.configure("2") do |config|
  config.vm.box = "opscode_ubuntu-12.04_provisionerless"
  config.vm.box_url = "https://opscode-vm-bento.s3.amazonaws.com/vagrant/opscode_ubuntu-12.04_provisionerless.box"

  config.vm.define 'app' do |c|
    c.vm.hostname = 'app.demo.devopscasts.com'
    c.vm.network :private_network, ip: "192.168.33.10"
    c.vm.provision :shell, :inline => <<-SH
      sudo /vagrant/provisioning/install app
    SH
  end
end
