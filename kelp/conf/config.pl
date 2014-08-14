{
    modules      => ['JSON::XS', 'Template::Toolkit'],
    modules_init => {
        'Template::Toolkit' => {
            ENCODING     => 'utf8',
            INCLUDE_PATH => "./views"
        }    
    }
}
