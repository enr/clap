
app {
    version = "0.1-SNAPSHOT"
    baseContext = '/clap'
    encoding = 'UTF-8'
}

// you can override default value for destination directory
project {
    source = 'website'
    destination = 'target/xite/clap'
}

plugins { 
    enabled = [ 'markdown',
                'resources']
}

markdown {
    extensions = ['md']
    code {
        template = '<script type="syntaxhighlighter" class="brush: %s"><![CDATA[%s]]></script>';
    }
}

resources {
    excludedFilenameSuffix = ['~']
}

