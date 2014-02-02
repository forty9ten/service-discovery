require 'rubygems'
require 'sinatra'
require 'redis'
require 'haml'

set :bind, '0.0.0.0'

$redis = Redis.new

get '/' do
  @keys = $redis.keys('*')

  haml <<-HAML.gsub(/^\s{4}/, '')
    !!!
    %html
      %body
        %dl
          - @keys.each do |key|
            %dt= key
            %dd= $redis.get(key)
        %form{method: 'post'}
          %label
            Key
            %input{type: 'text', name: 'key'}
          %label
            Value
            %input{type: 'text', name: 'value'}
          %input{type: 'submit'}
  HAML
end

post '/' do
  $redis.set(params[:key], params[:value])
  redirect to('/')
end
